package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.models.ErrorDetail;
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
    //--------------------------------------------------------------------------------------------------
    ///GET ALL  /BOOKS
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
    //-----------------------------------------------------------------------------------
    ////DELETE /DATA/BOOKS/{ID}

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

    //--------------------------------------------------------------------------------------------
//    PUT /data/books/{id} - updates a books info (Title, Copyright, ISBN)
//    but does NOT have to assign authors to the books.
    @ApiOperation(value = "Updates a books info but no assigned author",
            notes = "updates a books info (Title, Copyright, ISBN)\n" +
                    "but does NOT have to assign authors to the books.",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Successful",
                    response = void.class)
    })
    @PutMapping(value = "/data/books/{id}")
    public ResponseEntity<?> updateBook(
            @RequestBody Book updateBook,
            @ApiParam(value = "Book id",
                    required = true,
                    example = "1")
            @PathVariable long id, HttpServletRequest request)
    {
        logger.info(request.getMethod() + " " + request.getRequestURI() + " accessed");
        booksService.update(updateBook, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ///-----------------------------------------------------------------------------------------------------
    //    POST /data/books/{bookid}/authors/{authorid} - assigns a book already in the system (bookid)
    //    to an author already in the system (authorid) (see how roles are handled for users
    @ApiOperation(value = "Adds a book to an author",
            response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201,
                    message = "Book added Successfully",
                    response = void.class),
            @ApiResponse(code = 500,
                    message = "Error adding Book",
                    response = ErrorDetail.class)
    })
    @PostMapping(value = "/data/books/{bookid}/authors/{authorid}")
    public ResponseEntity<?> addBookToAuthors(
            @PathVariable long bookid,
            @PathVariable long authorid
    )
    {
        booksService.assignAuthor(bookid, authorid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}