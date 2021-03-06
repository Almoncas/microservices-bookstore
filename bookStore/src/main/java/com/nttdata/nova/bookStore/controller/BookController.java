package com.nttdata.nova.bookStore.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.nttdata.nova.bookStore.dto.BookRegistryDto;
import com.nttdata.nova.bookStore.exception.InvalidDateException;
import com.nttdata.nova.bookStore.exception.InvalidEditorialException;
import com.nttdata.nova.bookStore.exception.InvalidIdException;
import com.nttdata.nova.bookStore.service.IBookRegistryService;
import com.nttdata.nova.bookStore.service.IBookService;
import com.nttdata.nova.bookStore.service.IEditorialService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("/book")
public class BookController {

	private enum bookOperation {CREATE, UPDATE, DELETE, FIND_ALL, FIND_ONE, SEARCH};
	
	@Autowired
	private IBookService bookService;

	@Autowired
	private IEditorialService editorialService;

	@Autowired
	private IBookRegistryService bookRegistryService;
	
	@PostMapping(path="/create",consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="Insert book", description = "Insert book method", tags={"BookRestServiceWrite"})
	public HttpEntity<BookDto> insertBook (@RequestBody BookDto book){
		if(book.getId()!=0) {
			throw new InvalidIdException(book.getId());
		}
		
		if(book.getPublish().after(Calendar.getInstance().getTime())) {
			throw new InvalidDateException();
		}
		
		if(editorialService.findById(book.getEditorial().getId())==null) {
			throw new InvalidEditorialException();
		}
		
		
		BookDto bookDto = bookService.save(book);
		
		if(bookDto!=null) {
			BookController.generateBookLinks(bookDto);
		}
		
		if(bookDto!=null && bookDto.getEditorial()!=null) {
			EditorialController.generateEditorialLinks(bookDto.getEditorial());
		}

		bookRegistryService.save(new BookRegistryDto(generateRegistryMessage(bookOperation.CREATE, bookDto!=null ? String.valueOf(bookDto.getId()) : null), Calendar.getInstance().getTime()));

		return new ResponseEntity<BookDto>(bookDto, HttpStatus.OK);
	}
	
	@PutMapping(path="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="Update book", description="Update book method", tags={"BookRestServiceWrite"})
	public HttpEntity<BookDto> updateBook(@RequestBody BookDto book){

		if(book.getId()==0) {
			throw new  InvalidIdException(book.getId());
		}
		
		if(book.getPublish().after(Calendar.getInstance().getTime())) {
			throw new InvalidDateException();
		}
		
		if(editorialService.findById(book.getEditorial().getId())==null) {
			throw new InvalidEditorialException();
		}
		
		
		
		BookDto bookDto = bookService.update(book);
		
		if(bookDto!=null) {
			BookController.generateBookLinks(bookDto);
		}
		
		if(bookDto!=null && bookDto.getEditorial()!=null) {
			EditorialController.generateEditorialLinks(bookDto.getEditorial());
		}

		bookRegistryService.save(new BookRegistryDto(generateRegistryMessage(bookOperation.UPDATE, bookDto!=null ? String.valueOf(bookDto.getId()) : null), Calendar.getInstance().getTime()));


		return new ResponseEntity<BookDto>(bookDto, HttpStatus.OK);
	}

	@GetMapping(path="/get", produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get all books", description = "Get all books method", tags={"BookRestServiceRead"})
	public HttpEntity<List<BookDto>> getAllBooks(){
		List<BookDto> bookDtoList = bookService.findAll();
		bookDtoList.forEach(b -> {
			BookController.generateBookLinks(b);
			EditorialController.generateEditorialLinks(b.getEditorial());
		});

		bookRegistryService.save(new BookRegistryDto(generateRegistryMessage(bookOperation.FIND_ALL, null), Calendar.getInstance().getTime()));

		return new ResponseEntity<List<BookDto>>(bookDtoList, HttpStatus.OK);
	}

	@GetMapping(path="/get/id/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Find a book by id", description = "Find a book by id method", tags={"BookRestServiceRead"})
	public HttpEntity<BookDto> getBookById(@PathVariable("id") Long id){
		BookDto bookDto = bookService.findById(id);

		if(bookDto!=null) {
			BookController.generateBookLinks(bookDto);
		}
		
		if(bookDto!=null && bookDto.getEditorial()!=null) {
			EditorialController.generateEditorialLinks(bookDto.getEditorial());
		}

		bookRegistryService.save(new BookRegistryDto(generateRegistryMessage(bookOperation.FIND_ONE,  bookDto!=null ? String.valueOf(bookDto.getId()) : null), Calendar.getInstance().getTime()));

		return new ResponseEntity<BookDto>(bookDto, HttpStatus.OK);
	}

	@GetMapping(path="/get/editorial", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Find a book by editorial", description = "Find a book by editorial method", tags={"BookRestServiceRead"})
	public HttpEntity<List<BookDto>> getBooksByEditorial(@PathVariable Long id){
		List<BookDto> bookDtoList = bookService.searchByEditorial(id);
		bookDtoList.forEach(b -> {
			if(b!=null) {
				BookController.generateBookLinks(b);
			}
			
			if(b!=null && b.getEditorial()!=null) {
				EditorialController.generateEditorialLinks(b.getEditorial());
			}
		});

		bookRegistryService.save(new BookRegistryDto(generateRegistryMessage(bookOperation.SEARCH, null), Calendar.getInstance().getTime()));

		return new ResponseEntity<List<BookDto>>(bookDtoList, HttpStatus.OK);
	}

	@GetMapping(path="/get/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Find a book by title", description = "Find a book by title method", tags={"BookRestServiceRead"})
	public HttpEntity<List<BookDto>> getBooksByTitle(@PathVariable("title") String title){
		List<BookDto> bookDtoList = bookService.searchByTitle(title);
		bookDtoList.forEach(b -> {
			if(b!=null) {
				BookController.generateBookLinks(b);
			}
			
			if(b!=null && b.getEditorial()!=null) {
				EditorialController.generateEditorialLinks(b.getEditorial());
			}
		});

		bookRegistryService.save(new BookRegistryDto(generateRegistryMessage(bookOperation.SEARCH, null), Calendar.getInstance().getTime()));

		return new ResponseEntity<List<BookDto>>(bookDtoList, HttpStatus.OK);
	}

	@DeleteMapping(path="/delete/{id}")
	@Operation(summary="Delete book", description = "Delete book method", tags={"BookRestServiceWrite"})
	public HttpEntity<String> deleteBook(@PathVariable("id") Long id){
		BookDto book=bookService.findById(id);
		bookService.delete(book);

		bookRegistryService.save(new BookRegistryDto(generateRegistryMessage(bookOperation.DELETE, id!=null ? String.valueOf(id) : null), Calendar.getInstance().getTime()));

		return new ResponseEntity<String>("Book with id=" + id + " was deleted",HttpStatus.OK);
	}

	public static void generateBookLinks(BookDto bookDto) {
		bookDto.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(bookDto.getId())).withSelfRel());
		bookDto.add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(EditorialController.class).getEditorialById(bookDto.getEditorial().getId()))
				.withRel("editorial"));
	}

	private String generateRegistryMessage(bookOperation operation, String id){
		String message=null;

		switch(operation){
		case CREATE: 
		message = "CREATE book with id " + id;
		break;
		
		case UPDATE:
			message = "UPDATE book with id " + id;
			break;
			
		case DELETE:
			message = "DELETE book with id " + id;
			break;
			
		case FIND_ALL:
			message = "FINAL_ALL books";
			break;
			
		case FIND_ONE:
			message = "FIND_ONE book with id " + id;
			break;
			
		case SEARCH:
			message = "SEARCHING books";
			break;			

		default:
			break;
		} 
		
		return message;
	}
}