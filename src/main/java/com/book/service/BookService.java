package com.book.service;

import com.javatechie.aws.api.Book;

public interface BookService {
    void save(Book book) throws Exception;
    void get();
    void update();
    void delete();
}
