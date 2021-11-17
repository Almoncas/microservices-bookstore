package com.nttdata.nova.bookStore.repositories;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nttdata.nova.bookStore.entities.Book;
import com.nttdata.nova.bookStore.entities.Editorial;

@Repository
public interface IBookRepository extends JpaRepository<Book,Long> {
	
	@Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
	public Book findByTitle(String title);
	
	@Query("SELECT b FROM Book b WHERE b.editorial LIKE :editorial")
	public List<Book> findByEditorial(@Param("editorial") Editorial search); 

}


