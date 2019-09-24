package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "book", description = "Book list")
@Entity
@Table(name = "book")
public class Book extends Auditable
{
    @ApiModelProperty(name = "bookId", value = "Key for linking books and authors", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookid;

    @ApiModelProperty(name = "bookTitle", value = "Title of book", required = true, example = "Lord of the Rings")
    @Column(nullable = false)
    private String title;
    @ApiModelProperty(name = "bookISBN", value = "Book's ISBN number", required = true, example = "8675309")
    private String ISBN;
    @ApiModelProperty(name = "bookCopy", value = "Year book was copyrighted", required = true, example = "1997")
    @Column(nullable = true)
    private int copy;



//    @ManyToMany
//    @JoinTable(name = "wrote", joinColumns = {@JoinColumn(name = "bookid")}, inverseJoinColumns = {@JoinColumn(name = "authorid")})
//    @JsonIgnoreProperties("book")
//    private List<Author> authors = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "wrote", joinColumns = {@JoinColumn(name = "bookid")}, inverseJoinColumns = {@JoinColumn(name =
            "authorid")})
    @JsonIgnoreProperties("book")
    private List<Author> author = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "authorid",
//            nullable = false)
//    @JsonIgnoreProperties({"book", "hibernateLazyInitializer"})
//    private Author authors;
//
//    @ManyToMany(mappedBy = "author")
//    List<Author> author = new ArrayList<>();

    public Book()
    {
    }

    public Book(String title, String ISBN, int copy)
    {
        this.title = title;
        this.ISBN = ISBN;
        this.copy = copy;
    }

    public long getBookid()
    {
        return bookid;
    }

    public void setBookid(long bookid)
    {
        this.bookid = bookid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getISBN()
    {
        return ISBN;
    }

    public void setISBN(String ISBN)
    {
        this.ISBN = ISBN;
    }

    public int getCopy()
    {
        return copy;
    }

    public void setCopy(int copy)
    {
        this.copy = copy;
    }

    public List<Author> getAuthor()
    {
        return author;
    }

    public void setAuthor(List<Author> author)
    {
        this.author = author;
    }
}