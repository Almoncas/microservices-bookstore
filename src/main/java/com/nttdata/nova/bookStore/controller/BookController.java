package com.nttdata.nova.bookStore.controller;

import java.util.Calendar;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.nova.bookStore.dto.BookDto;
import com.nttdata.nova.bookStore.exception.InvalidDateException;
import com.nttdata.nova.bookStore.exception.InvalidEditorialException;
import com.nttdata.nova.bookStore.exception.InvalidIdException;
import com.nttdata.nova.bookStore.service.IBookService;
import com.nttdata.nova.bookStore.service.IEditorialService;



import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("/bookstore")
public class BookController {
	
	@Autowired
	private IBookService bookService;

	@Autowired
	private IEditorialService editorialService;
	
	@PostMapping(path="/create",consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="Insert book", description = "Insert book method", tags={"BookRestServiceWrite"})
	public HttpEntity<BookDto> insertBook (@RequestBody BookDto book){
		
		if(book.getId()!=0){
			throw new InvalidIdException(book.getId());
		}

		if(book.getPublish().after(Calendar.getInstance().getTime())){
			throw new InvalidDateException();
		}

		if(editorialService.findById(book.getEditorial().getId())==null){
			throw new InvalidEditorialException();
		}

		BookDto bookDto=bookService.save(book);

		return new ResponseEntity<BookDto>(bookDto,HttpStatus.OK);
	}
	
	@PutMapping(path="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="Update book", description="Update book method", tags={"BookRestServiceWrite"})
	public HttpEntity<BookDto> updateBook(@RequestBody BookDto book){
		
		if(book.getId()==0){
			throw new InvalidIdException(book.getId());
		}

		if(book.getPublish().after(Calendar.getInstance().getTime())){
			throw new InvalidDateException();
		}

		if(editorialService.findById(book.getEditorial().getId())==null){
			throw new InvalidEditorialException();
		}

		return new ResponseEntity<BookDto>(bookService.update(book),HttpStatus.OK);
	}

	@GetMapping(path="/get", produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get all books", description = "Get all books method", tags={"BookRestServiceRead"})
	public HttpEntity<List<BookDto>> getAllBooks(){
		return new ResponseEntity<List<BookDto>>(bookService.findAll(),HttpStatus.OK);
	}

	@GetMapping(path="/get/id/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Find a book by id", description = "Find a book by id method", tags={"BookRestServiceRead"})
	public HttpEntity<BookDto> getBookById(@PathVariable("id") long id){
		return new ResponseEntity<BookDto>(bookService.findById(id),HttpStatus.OK);
	}

	@GetMapping(path="/get/editorial", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Find a book by editorial", description = "Find a book by editorial method", tags={"BookRestServiceRead"})
	public HttpEntity<List<BookDto>> getBooksByEditorial(@PathVariable long id){
		return new ResponseEntity<List<BookDto>>(bookService.searchByEditorial(id),HttpStatus.OK);
	}

	@GetMapping(path="/get/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Find a book by title", description = "Find a book by title method", tags={"BookRestServiceRead"})
	public HttpEntity<List<BookDto>> getBooksByTitle(@PathVariable("title") String title){
		return new ResponseEntity<List<BookDto>>(bookService.searchByTitle(title),HttpStatus.OK);
	}

	@DeleteMapping(path="/delete/{id}")
	@Operation(summary="Delete book", description = "Delete book method", tags={"BookRestServiceWrite"})
	public HttpEntity<String> deleteBook(@PathVariable("id") long id){
		BookDto book=bookService.findById(id);
		bookService.delete(book);

		return new ResponseEntity<String>("Book with id=" + id + " was deleted",HttpStatus.OK);
	}

}