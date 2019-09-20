package com.lambdaschool.starthere.services;


import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.repository.AuthorsRepository;
import com.lambdaschool.starthere.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "booksService")
public class BooksServiceImpl implements BooksService
{
    @Autowired
    private BooksRepository booksrepo;

    @Autowired
    private AuthorsRepository authorsrepo;

    @Override
    public List<Book> findAll(Pageable pageable)
    {
        List<Book> bookList = new ArrayList<>();
        booksrepo.findAll(pageable).iterator().forEachRemaining(bookList::add);
        return bookList;
    }

    @Transactional
    @Override
    public Book updateBook(Book book, long id)
    {
        Book currentB = booksrepo.findById(id).orElseThrow(EntityNotFoundException::new);
        if (book.getBooktitle() != null)
        {
            currentB.setBooktitle((book.getBooktitle()));
        }
        if (book.getISBN() != null)
        {
            currentB.setISBN((book.getISBN()));
        }
        if (book.getAuthors() != null && book.getAuthors().size() > 0)
        {
            currentB.setAuthors((book.getAuthors()));
        }
        if (book.getCopy() < 0)
        {
            currentB.setCopy((book.getCopy()));
        }

        booksrepo.save(currentB);
        return  currentB;
    }

    @Override
    public void delete(long id)
    {
        if(booksrepo.findById(id).isPresent())
        {
            booksrepo.deleteById(id);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Transactional
    @Override
    public void assignAuthor(long bookid, long authorid)
    {
        Book currentB = booksrepo.findById(bookid).orElseThrow(EntityNotFoundException::new);
        currentB.getAuthors().add(authorsrepo.findById(authorid).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void save(Book book)
    {
        booksrepo.save(book);
    }
}