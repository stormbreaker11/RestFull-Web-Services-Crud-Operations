package com.example.exception.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.exception.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {

}
