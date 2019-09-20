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

        @ApiModelProperty(name = "firstname",
                value = "Authors First Name",
                required = true,
                example = "Spongebob")
        private String firstname;

        @ApiModelProperty(name = "lastname",
                value = "Authors Last Name",
                required = true,
                example = "Squarepants")
        private String lastname;

        @ManyToMany(mappedBy = "authors")
        @JsonIgnoreProperties(value = "authors")
        private List<Book> books = new ArrayList<>();

        public Author() {

        }

        //constructor

        public Author(String firstname, String lastname, List<Book> books) {
            this.firstname = firstname;
            this.lastname = lastname;
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

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public List<Book> getBooks() {
            return books;
        }

        public void setBooks(List<Book> books) {
            this.books = books;
        }
    }
