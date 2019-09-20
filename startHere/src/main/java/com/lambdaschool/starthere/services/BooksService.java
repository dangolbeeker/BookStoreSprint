package com.lambdaschool.starthere.services;


import com.lambdaschool.starthere.models.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BooksService
{
    List<Book> findAll(Pageable pageable);

    Book update( Book book, long id);

    void delete(long id);

    void assignAuthor(long bookid, long authorid);

    void save(Book book);
}