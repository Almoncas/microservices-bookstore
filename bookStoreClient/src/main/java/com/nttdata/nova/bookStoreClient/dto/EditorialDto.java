package com.nttdata.nova.bookStoreClient.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.hateoas.RepresentationModel;

public class EditorialDto extends RepresentationModel<EditorialDto> implements Serializable{

	private static final long serialVersionUID = 1L;


	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("nombre")
	private String name;
	

	public EditorialDto() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
