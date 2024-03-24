package com.example.userservice.services;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.PasswordMismatchException;
import com.example.userservice.exceptions.SessionNotFoundException;
import com.example.userservice.exceptions.UserAlreadyExistException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Session;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.models.User;
import com.example.userservice.repositories.SessionRepository;
import com.example.userservice.repositories.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;


import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey secretKey;
    @Autowired
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secretKey = Jwts.SIG.HS256.key().build();
    }

    public ResponseEntity<UserDto> login(String email, String password) throws UserNotFoundException, PasswordMismatchException {
         User user  = userRepository.findByEmail(email)
                 .orElseThrow(()->new UserNotFoundException("User Not Register:First Register"));
        UserDto userDto = UserDto.from(user);
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new PasswordMismatchException("Provided password was InCorrect");
        }
        Map<String, Object> jwtData = new HashMap<>();
        jwtData.put("email", email);
        jwtData.put("createdAt", new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        jwtData.put("expiryAt", calendar.getTime());
        String token =Jwts.builder().claims(jwtData).signWith(secretKey).compact();

        Session session = new Session();
//        String token = RandomStringUtils.randomAlphabetic(30);
        session.setToken(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setExpiryAt(calendar.getTime());
        sessionRepository.save(session);

        MultiValueMap<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token"+token);
        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }

    public ResponseEntity<String> logout(String userId, String token) throws UserNotFoundException, SessionNotFoundException {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()->new UserNotFoundException("User Not Exist"));
        Session session = sessionRepository.findSessionByTokenAndUser(token, user).orElseThrow(()->new SessionNotFoundException("Session Not Found"));
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return ResponseEntity.ok("Logged out successfully");
    }

    public ResponseEntity<UserDto> signup(String email, String password) throws UserAlreadyExistException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserAlreadyExistException("Provided Email Already Exist");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        UserDto userDto = UserDto.from(userRepository.save(user));
        return ResponseEntity.ok(userDto);
    }

    public SessionStatus validate(String userId, String token) throws UserNotFoundException {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()->new UserNotFoundException("User Not Exist"));
        Optional<Session> optionalSession = sessionRepository.findSessionByTokenAndUser(token, user);
        if(optionalSession.isEmpty()) return SessionStatus.ENDED;
        Session session = optionalSession.get();
        if(session.getSessionStatus().equals(SessionStatus.ENDED)||new Date().compareTo(session.getExpiryAt())>0){
            return SessionStatus.ENDED;
        }
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            return SessionStatus.ENDED;
        }
        return SessionStatus.ACTIVE;

    }
    public SessionStatus validate( String token){
         Optional<Session> optionalSession = sessionRepository.findSessionByToken(token);
        if(optionalSession.isEmpty()) return SessionStatus.ENDED;
        Session session = optionalSession.get();
        if(session.getSessionStatus().equals(SessionStatus.ENDED)||new Date().compareTo(session.getExpiryAt())>0){
            return SessionStatus.ENDED;
        }
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            return SessionStatus.ENDED;
        }
        return SessionStatus.ACTIVE;
    }
}
