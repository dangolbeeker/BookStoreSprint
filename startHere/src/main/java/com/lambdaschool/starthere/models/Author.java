package com.lambdaschool.starthere.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Author", description = "The Author Model")
@Entity
@Table(name = "author")
public class Author extends Auditable
{
    @ApiModelProperty(name = "authorid",
            value = "Primary key for the author",
            required = true,
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorid;

    @ApiModelProperty(name = "fname",
            value = "Authors First Name",
            required = true,
            example = "Spongebob")
    @Column
    private String fname;

    @ApiModelProperty(name = "lname",
            value = "Authors Last Name",
            required = true,
            example = "Squarepants")
    private String lname;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnoreProperties(value = "authors")
    private List<Book> books = new ArrayList<>();

    public Author() {

    }

    //constructor

    public Author(String fname, String lname, List<Book> books) {
        this.fname = fname;
        this.lname = lname;
        this.books = books;
    }

    //methods
    //getters and setters


    public long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(long authorid) {
        this.authorid = authorid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}