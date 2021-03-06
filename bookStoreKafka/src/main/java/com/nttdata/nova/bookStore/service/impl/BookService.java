package com.nttdata.nova.bookStore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nttdata.nova.bookStore.dto.BookDto;
import com.nttdata.nova.bookStore.entities.Book;
import com.nttdata.nova.bookStore.repositories.IBookRepository;
import com.nttdata.nova.bookStore.service.IBookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService{
    
    @Autowired
    private IBookRepository bookRepository;

    @Override
    public BookDto save(BookDto bookDto){
        bookDto.setId(null);
        return new BookDto(bookRepository.save(new Book(bookDto)));
    }

    @Override
    public BookDto update (BookDto bookDto){
        return new BookDto(bookRepository.save(new Book(bookDto)));
    }

    @Override
    public void delete(BookDto bookDto){
        bookRepository.delete(new Book(bookDto));
    }

    @Override
    public BookDto findById(Long id){
        Optional<Book> book = bookRepository.findById(id);
        return book.isPresent() ? new BookDto(book.get()) : null;
    }

    @Override 
    public List<BookDto> findAll(){
        List<BookDto> bookDtoList=new ArrayList<BookDto>();
        
        List<Book> bookList=(List<Book>) bookRepository.findAll();
        bookList.stream().forEach(b -> bookDtoList.add(new BookDto(b)));

        return bookDtoList;
    }

    @Override
    public List<BookDto> searchByTitle(String search){
        List<BookDto> bookDtoList = new ArrayList<BookDto>();

        List<Book> bookList = (List<Book>) bookRepository.searchByTitle(search);
        bookList.stream().forEach(b -> bookDtoList.add(new BookDto(b)));

        return bookDtoList;
    }

    @Override
    public List<BookDto> searchByEditorial(Long id){
        List<BookDto> bookDtoList = new ArrayList<BookDto>();

        List<Book> bookList = (List<Book>) bookRepository.searchByEditorial(id);
        bookList.stream().forEach(b ->  bookDtoList.add(new BookDto(b)));

        return bookDtoList;
    }
}
