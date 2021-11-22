package com.nttdata.nova.bookStore.repositories;

import com.nttdata.nova.bookStore.entities.BookRegistry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRegistryRepository extends MongoRepository<BookRegistry,String> {
    
}
