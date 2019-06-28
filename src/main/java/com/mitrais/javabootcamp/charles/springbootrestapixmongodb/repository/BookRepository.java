package com.mitrais.javabootcamp.charles.springbootrestapixmongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mitrais.javabootcamp.charles.springbootrestapixmongodb.entity.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}
