package com.nttdata.nova.bookStore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.nttdata.nova.bookStore.entities.Editorial;
import com.nttdata.nova.bookStore.repositories.IEditorialRepository;

@DataJpaTest
@Sql("testdata.sql")
public class EditorialDataJpaTest {
	
	@Autowired
	IEditorialRepository editorialRepository;
	
	
	@Test
	public void deleteByIdTest() {
		editorialRepository.deleteById((long)100);
		assertThat(editorialRepository.findById((long)100).isEmpty());
	}
	
	@Test
	public void existsByIdTest() {
		assertThat(editorialRepository.existsById((long)100)).isNotNull();
	}
	
	@Test
	public void addEditorialTest() {
		Editorial ed=buildEditorial();
		assertThat(editorialRepository.save(ed)).isNotNull();
	}
	
	@Test
	public void findByNameTest() {
		assertThat(editorialRepository.findByName("Editorial100")).isNotNull();
	}
	
	public Editorial buildEditorial() {
		Editorial editorial=new Editorial();
		editorial.setId((long)400);
		editorial.setName("Editorial400");
		
		return editorial;
	}
	

}
