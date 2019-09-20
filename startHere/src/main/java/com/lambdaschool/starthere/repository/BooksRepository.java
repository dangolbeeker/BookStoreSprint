package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BooksRepository extends PagingAndSortingRepository<Book, Long>
{

}