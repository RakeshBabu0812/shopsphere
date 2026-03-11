package com.eproject.Eproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eproject.Eproject.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
	
	

	
}
