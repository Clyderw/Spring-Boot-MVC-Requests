package com.prodRepo.prodRepo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prodRepo.prodRepo.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
