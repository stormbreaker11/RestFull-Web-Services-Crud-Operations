package com.example.exception.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.model.User;
import com.example.exception.repo.UserRepository;

@Service
public class UserServiceImpli implements UserService {

	@Autowired
	UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUserById(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUser(User currentUser) {
		repository.save(currentUser);

	}

	@Override
	public Optional<User> findById(int id) {
		
		return repository.findById(id);
	}
}
