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

//@Transactional
@Service
public class AuthorServiceImpl implements AuthorService
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
    public List<Author> findAll(Pageable pageable)
    {
        List<Author> list = new ArrayList<>();
        authorRepository.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Author findAuthorById(long id) throws EntityNotFoundException
    {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (authorRepository.findById(id).isPresent())
        {
            authorRepository.deleteById(id);
        }
        else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Author save(Author author)
    {
            Author newAuthor = new Author();

            newAuthor.setFname(author.getFname());
            newAuthor.setLname(author.getLname());

            for (Book b : author.getBook())
            {
                newAuthor.getBook().add(new Book(b.getTitle(), b.getISBN(), b.getCopy()));
            }

            return authorRepository.save(newAuthor);
    }

    @Override
    public Author update(Author author, long id)
    {
        return null;
    }
}
