package com.eproject.Eproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eproject.Eproject.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional<User> findByEmail(String email);
	

}
