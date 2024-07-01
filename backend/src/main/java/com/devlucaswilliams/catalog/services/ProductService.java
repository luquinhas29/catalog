package com.devlucaswilliams.catalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devlucaswilliams.catalog.dto.ProductDTO;
import com.devlucaswilliams.catalog.entities.Product;
import com.devlucaswilliams.catalog.repositories.ProductRepository;
import com.devlucaswilliams.catalog.services.exception.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(PageRequest pageRequest){
		Page<Product> page = productRepository.findAll(pageRequest);
		return page.map(x -> new ProductDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("resource not found"));
		return new ProductDTO(entity, entity.getCategories());
	}
	
}
