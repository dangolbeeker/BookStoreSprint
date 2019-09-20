package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Author;
import com.lambdaschool.starthere.repository.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "authorsService")
public class AuthorsServiceImpl implements  AuthorsService
{
    @Autowired
    private AuthorsRepository authorrepo;

    @Override
    public List<Author> findAll(Pageable pageable)
    {
        List<Author> authorList = new ArrayList<>();
        authorrepo.findAll(pageable).iterator().forEachRemaining(authorList::add);
        return authorList;
    }


    @Override
    public void save(Author author)
    {
        authorrepo.save(author);
    }
}