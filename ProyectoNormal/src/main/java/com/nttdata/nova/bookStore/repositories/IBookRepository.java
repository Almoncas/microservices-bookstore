package com.nttdata.nova.bookStore.repositories;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nttdata.nova.bookStore.entities.Book;

@Repository
public interface IBookRepository extends JpaRepository<Book,Long> {
	
	@Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
	public List<Book> searchByTitle(String title);
	
	@Query("SELECT b FROM Book b WHERE b.editorial.id LIKE :id")
	public List<Book> searchByEditorial(@Param("id") Long id); 

}


