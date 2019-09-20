package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Book", description = "The Book Model")
@Entity
@Table(name = "book")
public class Book extends Auditable
    {
    @ApiModelProperty(name = "bookid",
            value = "Primary key for the book",
            required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookid;

    @ApiModelProperty(name = "booktitle",
            value = "Book Title",
            required = true,
            example = "Java For Dummies")
    private String booktitle;

    @ApiModelProperty(name = "ISBN",
            value = "The ISBN number of the book",
            required = true,
            example = "9797979797979797979")
    private String ISBN;

    @ApiModelProperty(name = "copy",
            value = "Copyright Date",
            example = "2017")
    private int copy;

    @ManyToMany
    @JsonIgnoreProperties(value = "books")
    @JoinTable(name = "author_book", joinColumns = {@JoinColumn(name = "bookid")},
            inverseJoinColumns = {@JoinColumn(name = "authorid")})
    private List<Author> authors = new ArrayList<>();

    ///default constructor
    public Book() {

    }

    public Book(String booktitle, String ISBN, int copy) {
        this.booktitle = booktitle;
        this.ISBN = ISBN;
        this.copy = copy;
    }


    //methods
    //getters and setters

    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getCopy() {
        return copy;
    }

    public void setCopy(int copy) {
        this.copy = copy;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    }
