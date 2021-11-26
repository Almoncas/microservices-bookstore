package com.nttdata.nova.bookStoreClient.controller;

import java.net.URI;
import java.net.URISyntaxException;

import com.nttdata.nova.bookStoreClient.dto.BookDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//@RequestMapping("/demo")
public class RestTemplateController {

        @Autowired
        RestTemplate restTemplate;

        final String baseURL ="Http://localhost:8081/bookstore/book";

        @GetMapping("/get")
        public ResponseEntity<BookDto[]> getAllBooks() throws URISyntaxException{
                URI uri = new URI(baseURL + "/get");
                return restTemplate.getForEntity(uri,BookDto[].class);
        }

        @GetMapping("get/{id}")
        public ResponseEntity<BookDto> getBookById(@PathVariable String id) throws URISyntaxException{
                URI uri = new URI(baseURL + "/get/id/" + id);
                return restTemplate.getForEntity(uri,BookDto.class);
        }

}
