package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "author", description = "List of authors")
@Entity
@Table(name = "author")
public class Author extends Auditable
{
    @ApiModelProperty(name = "authorid", value = "Key for linking books and authors", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorid;

    @ApiModelProperty(name = "fName", value = "Author's first name", required = true, example = "Bob")
    private String fname;
    @ApiModelProperty(name = "lName", value = "Author's last name", required = true, example = "Saget")
    private String lname;

//    @OneToMany(mappedBy = "author",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    @JsonIgnoreProperties("author")
//    private List<Book> book = new ArrayList<>();

//    @ManyToMany(mappedBy = "author")
//    @JsonIgnoreProperties("author")
//    private List<Book> book = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "wrote", joinColumns = {@JoinColumn(name = "bookid")}, inverseJoinColumns = {@JoinColumn(name =
            "authorid")})
    @JsonIgnoreProperties("author")
    private List<Book> book = new ArrayList<>();

   public Author()
   {
   }

    public Author(String fName, String lName, List<Book> book)
    {
        this.fname = fName;
        this.lname = lName;
        this.book = book;
    }

    public long getAuthorId()
    {
        return authorid;
    }

    public void setAuthorId(long authorid)
    {
        this.authorid = authorid;
    }

    public String getFname()
    {
        return fname;
    }

    public void setFname(String fname)
    {
        this.fname = fname;
    }

    public String getLname()
    {
        return lname;
    }

    public void setLname(String lname)
    {
        this.lname = lname;
    }

    public List<Book> getBook()
    {
        return book;
    }

    public void setBook(List<Book> book)
    {
        this.book = book;
    }
}