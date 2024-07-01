package com.devlucaswilliams.catalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devlucaswilliams.catalog.dto.CategoryDTO;
import com.devlucaswilliams.catalog.entities.Category;
import com.devlucaswilliams.catalog.repositories.CategoryRepository;
import com.devlucaswilliams.catalog.services.exception.DataBaseException;
import com.devlucaswilliams.catalog.services.exception.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(PageRequest pageRequest) {
		Page<Category> page = categoryRepository.findAll(pageRequest);
		return page.map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("not found"));
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		categoryRepository.save(entity);
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
		Category entity = new Category();
		entity = categoryRepository.getReferenceById(id);
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		return new CategoryDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("not found" +id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		
		if(!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("resource not found " +id);
		}
		try{
			categoryRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException("database not found " +id);
		}
		
		
	}

}
