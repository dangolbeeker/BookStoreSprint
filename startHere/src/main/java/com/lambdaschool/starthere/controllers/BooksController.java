package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.services.BooksService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController
{

    private static final Logger logger = LoggerFactory.getLogger(BooksController.class);

    @Autowired
    private BooksService booksService;

    @ApiOperation(value = "Return all Books",
            response = Book.class,
            responseContainer = "List")
    @ApiImplicitParams({@ApiImplicitParam(name = "page",
            dataType = "integer",
            paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size",
                    dataType = "integer",
                    paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort",
                    allowMultiple = true,
                    dataType = "string",
                    paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). "
                            + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
    @GetMapping(value ="/books", produces = {"application/json"})
    public ResponseEntity<?> findAllBooks(HttpServletRequest request,
                                          @PageableDefault(page = 0,
                                                  size = 3)
                                                  Pageable pageable) {
        logger.info(request.getMethod().toUpperCase() + " " +
                request.getRequestURI() + " accessed at warn level");
        List<Book> myBooks = booksService.findAll(pageable);
        return new ResponseEntity<>(myBooks, HttpStatus.OK);
    }
    //---------------------------------------------------------------

    @ApiOperation(value="Deletes a Book Based on Id",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Book Successfully Deleted",
                    response = void.class)
    })
    @DeleteMapping("/books/{bookid}")
    public ResponseEntity<?> deleteCourseById(
            @ApiParam(value = "Books id",
                    required = true,
                    example = "1")
            @PathVariable long bookid,
            HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        booksService.delete(bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}