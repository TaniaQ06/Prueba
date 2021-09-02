package com.book.service;

import com.javatechie.aws.api.Book;

import java.util.List;

public interface BookService {
    void save(Book book) throws Exception;
    Object get(String id) throws Exception;
    Object update(String id, Book book) throws Exception;
    Object delete(String id) throws Exception;
    List<Book> getAllBooks() throws Exception;
}
