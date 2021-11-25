package com.nttdata.nova.bookStore.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.nttdata.nova.bookStore.dto.BookRegistryDto;
import com.nttdata.nova.bookStore.entities.BookRegistry;
import com.nttdata.nova.bookStore.repositories.IBookRegistryRepository;
import com.nttdata.nova.bookStore.service.IBookRegistryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class BookRegistryService implements IBookRegistryService {

    @Autowired
    IBookRegistryRepository methodRegistryRepository;

    @Override
    @CacheEvict(value="registries", cacheManager="cacheManager", allEntries=true)
    @PreAuthorize("hasRole('admin')")
    public BookRegistryDto save(BookRegistryDto methodRegistryDto){
        methodRegistryDto.setId(String.valueOf(methodRegistryRepository.findAll().size()+1));
        
        return new BookRegistryDto(methodRegistryRepository.save(new BookRegistry(methodRegistryDto)));

    }

    @Override
    @Cacheable(value="registries", cacheManager="cacheManager")
    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public List<BookRegistryDto> findAll(){
        List<BookRegistryDto> methodRegistryDtoList = new ArrayList<BookRegistryDto>();
        
        List<BookRegistry> methodRegistryList = (List<BookRegistry>)methodRegistryRepository.findAll();
        methodRegistryList.forEach(m -> methodRegistryDtoList.add(new BookRegistryDto(m)));

        return methodRegistryDtoList;
    }
    
}
