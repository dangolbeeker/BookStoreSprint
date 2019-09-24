package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Author;
import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.repository.AuthorRepository;
import com.lambdaschool.starthere.repository.BookRepository;
import com.lambdaschool.starthere.repository.RoleRepository;
import com.lambdaschool.starthere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BookServiceImpl implements BookService
{
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll(Pageable pageable)
    {
        List<Book> list = new ArrayList<>();
        bookRepository.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Book findBookById(long id)
    {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (bookRepository.findById(id).isPresent())
        {
            bookRepository.deleteById(id);
        }
        else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Override
    public Book save(Book book)
    {
        Book newBook = new Book();

//        newBook.setAuthor(book.getAuthor());
//        newBook.setBookid(book.getBookid());
        newBook.setCopy(book.getCopy());
        newBook.setTitle(book.getTitle());
        newBook.setISBN(book.getISBN());


//        for (Author a : book.getAuthor())
//        {
//            newBook.getAuthor().add(a);
//        }

        return bookRepository.save(newBook);
    }

    @Override
    public Book update(Book book, long id)
    {
        Book updateBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        if (book.getTitle() != null)
        {
            updateBook.setTitle(book.getTitle());
            updateBook.setISBN(book.getISBN());
            updateBook.setCopy(book.getCopy());
        }

        return bookRepository.save(updateBook);
    }
}