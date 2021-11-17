package com.nttdata.nova.bookStore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.nttdata.nova.bookStore.entities.Book;
import com.nttdata.nova.bookStore.entities.Editorial;
import com.nttdata.nova.bookStore.repositories.IBookRepository;



@DataJpaTest
@Sql("testdata.sql")
public class BookDataJpaTest {
	
	@Autowired
	IBookRepository bookRepository;
	
	@Test
	public void addBookTest() {
		Book bo=buildBook();
		assertThat(bookRepository.save(bo)).isNotNull(); //El save ya te devuelve el objeto totalmente creado, con Id y todo
	}
	
	@Test
	public void deleteBookbyIdTest() {
		bookRepository.deleteById((long)200);
		assertThat(bookRepository.findById((long)200)).isEmpty();
	}
	
	@Test
	public void deleteAllTest() {
		bookRepository.deleteAll();
		assertThat(bookRepository.findAll().isEmpty());
	}
	
	@Test
	public void findByIdTest() {
		assertThat(bookRepository.findById((long)200)).isNotNull();
	}
	
	@Test
	public void existsByIdTest() {
		assertThat(bookRepository.existsById((long)200)).isNotNull();
	}
	
	@Test
	public void findByTitleTest() {
		assertThat(bookRepository.findByTitle("Libro200")).isNotNull();
	}
	
	@Test
	public void findByEditorialTest() {
		Editorial ed=new Editorial();
		ed.setId((long)100);
		ed.setName("Editorial100");
		assertThat(bookRepository.findByEditorial(ed)).isNotNull();
	}

	
	public Book buildBook() {
		Book book=new Book();
		book.setId((long)4);
		book.setTitle("Libro4");
		book.setAuthor("Autor4");

		Calendar calendar=Calendar.getInstance();
		calendar.set(2021, 12, 12);
		
		Date date1=calendar.getTime();
		
		book.setPublish(date1);
		book.setPages(500);
		book.setDescription("Descripcion del libro4");
		Editorial editorial=new Editorial();
		editorial.setId(100);
		editorial.setName("Editorial 100");
		book.setEditorial(editorial);
		
		return book;
		
		
		
	}

}
