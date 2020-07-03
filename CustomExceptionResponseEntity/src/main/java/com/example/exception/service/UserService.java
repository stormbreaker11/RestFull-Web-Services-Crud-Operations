package com.example.exception.service;

import java.util.List;
import java.util.Optional;

import com.example.exception.model.User;

public interface UserService {

	public List<User> findAll();

	public Optional<User> findById(int id);

	public void deleteAllUsers();

	public void deleteUserById(long id);

	public void updateUser(User currentUser);

	
}
