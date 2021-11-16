package com.nttdata.nova.bookStore.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="editorial")
public class Editorial {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="editorial_seq")
	@SequenceGenerator(name = "editorial_seq", sequenceName = "editorial_sequence", allocationSize = 1)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy="editorial",cascade=CascadeType.REMOVE,orphanRemoval = true)
	private Set<Book> BookList ;

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
		return BookList;
	}

	public void setBookList(Set<Book> bookList) {
		BookList = bookList;
	}

}
