package com.nttdata.nova.bookStore.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.nova.bookStore.entities.Editorial;

@Repository
public interface IEditorialRepository extends JpaRepository<Editorial,Long> {
	
	public List<Editorial> findByName(String name);

}
