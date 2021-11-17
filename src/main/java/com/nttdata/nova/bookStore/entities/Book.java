package com.nttdata.nova.bookStore.entities;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="book")
public class Book implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="book_seq")
	@SequenceGenerator(name = "book_seq", sequenceName = "book_sequence", allocationSize = 1)
	private Long id;
	
	@Column(name="title", nullable=false, length=150)
	private String title;
	
	@Column(name="author", nullable=false, length=150)
	private String author;
	
	@Column(name="publish",nullable=false)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date publish;
	
	@Column(name="pages", nullable=false)
	private int pages;
	
	@Column(name="description",nullable=true,length=500)
	private String description;
	
	@ManyToOne(fetch =FetchType.EAGER, optional=false)
	@JoinColumn(name="id_editorial", nullable=false)
	private Editorial editorial;
	
	public Book() {
	}
	
	public Book(String title, String author, Date publish, Integer pages, String description, Editorial editorial) {
		this.title=title;
		this.author=author;
		this.publish=publish;
		this.pages=pages;
		this.description=description;
		this.editorial=editorial;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public java.util.Date getPublish() {
		return publish;
	}

	public void setPublish(java.util.Date date1) {
		this.publish = date1;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 
	
	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}
	

}
