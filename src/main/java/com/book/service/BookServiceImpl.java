package com.book.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.javatechie.aws.api.Book;

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
            dynamoDBMapper.save(book);
        } catch (IllegalArgumentException | AmazonServiceException e) {
            throw new Exception("Error saving book");
        }
    }

    @Override
    public void get() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
