package dev.snigdha.productservice.controllers;

import dev.snigdha.productservice.dtos.GenericProductDto;
import dev.snigdha.productservice.dtos.SearchRequestDto;
import dev.snigdha.productservice.services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.GeneralPath;

@RestController
@RequestMapping("search")
public class SearchController {
    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    @PostMapping
    public Page<GenericProductDto> search(@RequestBody SearchRequestDto searchRequestDto){
        return searchService.search(searchRequestDto.getQuery(), searchRequestDto.getPageNumber(), searchRequestDto.getPageSize());
    }
}
