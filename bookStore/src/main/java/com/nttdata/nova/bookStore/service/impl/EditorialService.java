package com.nttdata.nova.bookStore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nttdata.nova.bookStore.dto.EditorialDto;
import com.nttdata.nova.bookStore.entities.Editorial;
import com.nttdata.nova.bookStore.repositories.IEditorialRepository;
import com.nttdata.nova.bookStore.service.IEditorialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class EditorialService implements IEditorialService {

    @Autowired
    private IEditorialRepository editorialRepository;

    @Override
	@CacheEvict(value="editorials", cacheManager="cacheManager", allEntries=true)
	@PreAuthorize("hasRole('admin')")
	public EditorialDto save(EditorialDto editorialDto) {
		editorialDto.setId(null);
		return new EditorialDto(editorialRepository.save(new Editorial(editorialDto)));
	}

	@Override
	@CacheEvict(value="editorials", cacheManager="cacheManager", allEntries=true)
	@PreAuthorize("hasRole('admin')")
	public EditorialDto update(EditorialDto editorialDto) {
		return new EditorialDto(editorialRepository.save(new Editorial(editorialDto)));
	}

	@Override
	@CacheEvict(value="editorials", cacheManager="cacheManager", allEntries=true)
	@PreAuthorize("hasRole('admin')")
	public void delete(EditorialDto editorialDto) {
		editorialRepository.delete(new Editorial(editorialDto));
	}

	@Override
	@Cacheable(value="editorials", cacheManager="cacheManager")
	@PreAuthorize("hasRole('admin') or hasRole('user')")
	public EditorialDto findById(Long id) {
		Optional<Editorial> editorial = editorialRepository.findById(id);
		return editorial.isPresent() ? new EditorialDto(editorial.get()) : null;
	}

	@Override
	@Cacheable(value="editorials", cacheManager="cacheManager")
	@PreAuthorize("hasRole('admin') or hasRole('user')")
	public List<EditorialDto> findAll() {
		List<EditorialDto> editorialDtoList = new ArrayList<EditorialDto>();
		
		List<Editorial> editorialList =  (List<Editorial>) editorialRepository.findAll();
		editorialList.stream().forEach(e -> editorialDtoList.add(new EditorialDto(e)));
		
		return editorialDtoList;
	}

	@Override
	@Cacheable(value="editorials", cacheManager="cacheManager")
	@PreAuthorize("hasRole('admin') or hasRole('user')")
	public List<EditorialDto> findByName(String name) {
		List<EditorialDto> editorialDtoList = new ArrayList<EditorialDto>();
		
		List<Editorial> editorialList =  (List<Editorial>) editorialRepository.findByName(name);
		editorialList.stream().forEach(e -> editorialDtoList.add(new EditorialDto(e)));
		
		return editorialDtoList;
	}
    
}
