package com.eproject.Eproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eproject.Eproject.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
