package com.book.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.javatechie.aws.api.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Objects;

public class BookServiceImpl implements BookService {

    private DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(
            AmazonDynamoDBClientBuilder.standard().build());

    private DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
            .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
            .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.EVENTUAL)
            .withTableNameOverride(null)
            .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING)
            .build();


    @Override
    public void save(Book book) throws Exception {
        try {
            Book bookRetrieved = dynamoDBMapper.load(Book.class, book.getId());
            if (!Objects.isNull(bookRetrieved)){
                throw new Exception("The book id already exists");
            }
             dynamoDBMapper.save(book);
        } catch (IllegalArgumentException | AmazonServiceException e) {
            throw new Exception("Error saving book " + e);
        }
    }

    @Override
    @ResponseBody
    public Object get(String id) throws Exception {
        try {
            Book bookRetrieved = dynamoDBMapper.load(Book.class, id);

            if (Objects.isNull(bookRetrieved)){
                return new ResponseEntity<>("A book with the id provided does not exists" , HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return bookRetrieved;
        } catch (IllegalArgumentException | AmazonServiceException e) {
            throw new Exception("Error getting book " + e);
        }
    }

    @Override
    @ResponseBody
    public Object update(String id, Book book) throws Exception {
        try {
            Book retrievedBook = dynamoDBMapper.load(Book.class, id);

            if (Objects.isNull(retrievedBook)){
                return new ResponseEntity<>("A book with the id provided does not exists" , HttpStatus.INTERNAL_SERVER_ERROR);
            }
            retrievedBook.setName(book.getName());
            retrievedBook.setPrice(book.getPrice());
            dynamoDBMapper.save(retrievedBook);
            return "El libro se actualizo. Nuevos valores: Nombre: " + retrievedBook.getName() + " Precio: " + retrievedBook.getPrice();
        } catch (IllegalArgumentException | AmazonServiceException e) {
            throw new Exception("Error getting book " + e);
        }
    }

    @Override
    public Object delete(String id) throws Exception {
        try {

            Book bookToBeDeleted = dynamoDBMapper.load(Book.class, id);
            if (Objects.isNull(bookToBeDeleted)){
                return new ResponseEntity<>("A book with the id provided does not exists" , HttpStatus.INTERNAL_SERVER_ERROR);
            }
            dynamoDBMapper.delete(bookToBeDeleted);
            return "Se elimino el libro " + bookToBeDeleted.getName() + " con id: " + bookToBeDeleted.getId() + " con un precio de: " + bookToBeDeleted.getPrice();
        } catch (IllegalArgumentException | AmazonServiceException e) {
            throw new Exception("Error deleting book " + e);
        }
    }

    @Override
    public List<Book> getAllBooks() throws Exception {
        try {
           return dynamoDBMapper.scan(Book.class, new DynamoDBScanExpression());
        } catch (IllegalArgumentException | AmazonServiceException e) {
            throw new Exception("Error getting all the books " + e);
        }

    }
}
