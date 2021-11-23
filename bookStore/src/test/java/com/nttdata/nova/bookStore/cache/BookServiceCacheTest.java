package com.nttdata.nova.bookStore.cache;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nttdata.nova.bookStore.entities.Editorial;
import com.nttdata.nova.bookStore.repositories.IBookRepository;
import com.nttdata.nova.bookStore.service.IBookService;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest()
@EnableCaching
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class BookServiceCacheTest {
	
	@Autowired
	private IBookService bookService;
	
	@MockBean
	private IBookRepository mockBookRepository;


	@Test
	public void findByIdTest() {
		bookService.findById(Long.valueOf(1));
		bookService.findById(Long.valueOf(1));

        verify(mockBookRepository, times(1)).findById(Long.valueOf(1));
	}
	

	@Test
	public void findAllTest() {
		bookService.findAll();
		bookService.findAll();
       
		verify(mockBookRepository, times(1)).findAll();
	}
	
	
	@Test
	public void searchByTitle() {
		bookService.searchByTitle("Aprendiendo microservicios");
		bookService.searchByTitle("Aprendiendo microservicios");
				
		verify(mockBookRepository, times(1)).searchByTitle("Aprendiendo microservicios");	
	}

	
	@Test
	public void searchByEditorial() {
		Editorial editorial = new Editorial();
		editorial.setId(Long.valueOf(1));
		editorial.setName("Ediciones Nova");

		bookService.searchByEditorial(editorial.getId());
		bookService.searchByEditorial(editorial.getId());
				
		verify(mockBookRepository, times(1)).searchByEditorial(BDDMockito.eq(editorial.getId()));//Puede que no funcione
	}
	
	
	
	
}