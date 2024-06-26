package com.devlucaswilliams.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devlucaswilliams.catalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
