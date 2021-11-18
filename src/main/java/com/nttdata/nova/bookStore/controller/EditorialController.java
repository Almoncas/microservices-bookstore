package com.nttdata.nova.bookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.nttdata.nova.bookStore.dto.EditorialDto;
import com.nttdata.nova.bookStore.exception.InvalidIdException;
import com.nttdata.nova.bookStore.service.IEditorialService;

@RestController
@RequestMapping("/editorial")
public class EditorialController {
	
	@Autowired
	IEditorialService editorialService;
	
	@PostMapping(path="/create", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="Insert editorial", description="Insert editorial method", tags={"EditorialRestServiceWrite"})
	public HttpEntity<EditorialDto> insertEditorial(@RequestBody EditorialDto editorial){
		if(editorial.getId()!= 0){
			throw new InvalidIdException(editorial.getId());
		}

		return new ResponseEntity<EditorialDto>(editorialService.save(editorial),HttpStatus.OK);
	}

	@PutMapping(path="/update", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="Update editorial", description="Update editorial method", tags={"EditorialRestServiceWrite"})
	public HttpEntity<EditorialDto> updateEditorial(@RequestBody EditorialDto editorial){
		if(editorial.getId()==0){
			throw new InvalidIdException(editorial.getId());
		}

		return new ResponseEntity<EditorialDto>(editorialService.update(editorial), HttpStatus.OK);
	}

	@GetMapping(path="/get", produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="Get all editorials", description="Get all editorials method", tags={"EditorialRestServiceRead"})
	public HttpEntity<List<EditorialDto>> getAllEditorial(){
		return new ResponseEntity<List<EditorialDto>>(editorialService.findAll(),HttpStatus.OK);
	}

	@GetMapping(path="/get/id/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Find an editorial by id", description = "Find an editorial by id method", tags={"EditorialRestServiceRead"})
	public HttpEntity<EditorialDto> getEditorialById(@PathVariable("id") long id){
		return new ResponseEntity<EditorialDto>(editorialService.findById(id),HttpStatus.OK);
	}

	@GetMapping(path="/get/name/{name}", produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Find an editorial by name", description = "Find an editorial by name method", tags={"EditorialRestServiceRead"})
	public HttpEntity<List<EditorialDto>> getEditorialByName(@PathVariable("name") String name){
		return new ResponseEntity<List<EditorialDto>>(editorialService.findByName(name),HttpStatus.OK);
	}

	@DeleteMapping(path="/delete/{id}")
	@Operation(summary="Delete editorial", description="Delete editorial method", tags={"EditorialRestServiceWrite"})
	public HttpEntity<String> deleteEditorial(@PathVariable("id") long id){
		EditorialDto editorial = editorialService.findById(id);
		editorialService.delete(editorial);

		return new ResponseEntity<String>("Editorial with id"+id+" was deleted",HttpStatus.OK);
	}
}
