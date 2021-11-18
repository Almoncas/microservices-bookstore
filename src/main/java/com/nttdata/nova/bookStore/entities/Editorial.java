package com.nttdata.nova.bookStore.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nttdata.nova.bookStore.dto.EditorialDto;


@Entity
@Table(name="EDITORIAL")
public class Editorial implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="editorial_seq")
	@SequenceGenerator(name = "editorial_seq", sequenceName = "editorial_sequence", allocationSize = 1)
	private Long id;
	
	@Column(name="NAME", nullable=false,length=150)
	private String name;
	
	@OneToMany(mappedBy="editorial", fetch=FetchType.LAZY) //,cascade=CascadeType.ALL)//,cascade=CascadeType.REMOVE,orphanRemoval = true)
	private Set<Book> bookList ;
	
	public Editorial() {
	}

	public Editorial(String name) {
		this.name = name;
	}
	
	public Editorial(EditorialDto editorialDto) {
		this.id = editorialDto.getId();
		this.name = editorialDto.getName();
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBookList() {
		return bookList;
	}

	public void setBookList(Set<Book> bookList) {
		this.bookList = bookList;
	}

}
