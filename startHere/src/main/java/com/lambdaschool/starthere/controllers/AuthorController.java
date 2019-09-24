package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Author;
import com.lambdaschool.starthere.services.AuthorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController
{
    @Autowired
    AuthorService authorService;

    @ApiOperation(value = "returns all authors", response = Author.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authors found", responseContainer ="List", response = Author.class),
            @ApiResponse(code = 404, message = "Authors not found", responseContainer ="List", response = Author.class),
            @ApiResponse(code = 500, message = "Error retrieving authors", responseContainer ="List", response =
                    Author.class)
    })
    @GetMapping(value = "/authors",
            produces = {"application/json"})
    public ResponseEntity<?> listAllAuthors(
            @PageableDefault(page = 0,
                    size = 3)
                    Pageable pageable)
    {
        List<Author> myAuthors = authorService.findAll(pageable);
        return new ResponseEntity<>(myAuthors, HttpStatus.OK);
    }
}
