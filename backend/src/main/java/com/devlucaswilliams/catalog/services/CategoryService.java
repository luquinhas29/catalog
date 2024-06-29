package com.devlucaswilliams.catalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devlucaswilliams.catalog.entities.Category;
import com.devlucaswilliams.catalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}

}
