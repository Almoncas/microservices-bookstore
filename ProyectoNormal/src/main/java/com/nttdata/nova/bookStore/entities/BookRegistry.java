package com.nttdata.nova.bookStore.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.nttdata.nova.bookStore.dto.BookRegistryDto;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("BookRegistry")
public class BookRegistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String message;

    private Date date;

    public BookRegistry(){
    }

    public BookRegistry(BookRegistryDto bookRegistryDto){
        this.id=bookRegistryDto.getId();
        this.message=bookRegistryDto.getMessage();
        this.date=bookRegistryDto.getDate();
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message=message;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date=date;
    }
    
}
