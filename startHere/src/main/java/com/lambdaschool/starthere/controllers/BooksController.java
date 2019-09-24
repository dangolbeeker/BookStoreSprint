package com.lambdaschool.starthere.controllers;


import com.lambdaschool.starthere.models.Book;

import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.services.BookService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController
{
    @Autowired
    BookService bookService;

    @ApiOperation(value = "returns all books", response = Book.class, responseContainer = "List")
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
            @ApiResponse(code = 200, message = "Books found", responseContainer ="List", response = Book.class),
            @ApiResponse(code = 404, message = "Books not found", responseContainer ="List", response = Book.class),
            @ApiResponse(code = 500, message = "Error retrieving authors", responseContainer ="List", response =
                    Book.class)
    })
    @GetMapping(value = "/all",
            produces = {"application/json"})
    public ResponseEntity<?> listAllBooks(
            @PageableDefault(page = 0,
                    size = 3)
                    Pageable pageable)
    {
        List<Book> myBooks = bookService.findAll(pageable);
        return new ResponseEntity<>(myBooks, HttpStatus.OK);
    }
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book deleted", responseContainer ="List", response = Book.class),
            @ApiResponse(code = 404, message = "Book not found", responseContainer ="List", response = Book.class),
            @ApiResponse(code = 500, message = "You swine", responseContainer ="List", response =
                    Book.class)
    })
    @DeleteMapping("/delete/{bookid}")
    public ResponseEntity<?> deleteBookById(
            @PathVariable
                    long bookid)
    {
        bookService.delete(bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found", responseContainer ="List", response = Book.class),
            @ApiResponse(code = 404, message = "Book not found", responseContainer ="List", response = Book.class),
            @ApiResponse(code = 500, message = "You swine", responseContainer ="List", response =
                    Book.class)
    })
    @GetMapping(value = "/{bookid}",
            produces = {"application/json"})
    public ResponseEntity<?> getBookById(@ApiParam(value = "Book id", required = true, example = "1")
                                               @PathVariable
                                                       Long bookid)
    {
        Book b = bookService.findBookById(bookid);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @PostMapping(value = "/new",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewBook(@Valid
                                              @RequestBody
                                                      Book newBook) throws URISyntaxException
    {
        newBook = bookService.save(newBook);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newBookURI =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{bookid}").buildAndExpand(newBook.getBookid()).toUri();
        responseHeaders.setLocation(newBookURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{bookid}")
    public ResponseEntity<?> updateBook(
            @RequestBody
                    Book updateBook,
            @PathVariable
                    long bookid)
    {
        bookService.update(updateBook, bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
