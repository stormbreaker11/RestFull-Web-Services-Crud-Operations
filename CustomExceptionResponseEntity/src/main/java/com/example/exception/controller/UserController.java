package com.example.exception.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.exception.error.CustomErrorType;
import com.example.exception.model.User;
import com.example.exception.service.UserService;

@RestController

public class UserController {
	// Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All
	// Users---------------------------------------------

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single

	@SuppressWarnings("unchecked")

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") int id) {
		Optional<User> user = userService.findById(id);
		User user2; 
		if (!user.isPresent()) {
			
			return new ResponseEntity<Object>(new CustomErrorType("User with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		user2=user.get();
		return new ResponseEntity<User>(user2, HttpStatus.OK);
	}

	// -------------------Create a user

	@PostMapping(value = "/user")
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {

		
		Optional<User> userStat = userService.findById(user.getId());
		if (!userStat.isPresent()) {
			userService.updateUser(user);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<String>("User : " + user.getName() + " is Created" ,headers, HttpStatus.CREATED);
		}
		
		
		return new ResponseEntity<Object>(
				new CustomErrorType("User : " + user.getName() + " already exist."),
				HttpStatus.CONFLICT);
	}

	// ------------------- Update a User

	@SuppressWarnings("unused")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)

	public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user) {

		Optional<User> currentUser = userService.findById(id);
		User user2 = currentUser.get();

		if (currentUser == null) {

			return new ResponseEntity<Object>(
					new CustomErrorType("Unable to upate. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}

		user2.setName(user.getName());
		user2.setSal(user.getSal());

		userService.updateUser(user2);
		return new ResponseEntity<User>(user2, HttpStatus.OK);

	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {

		Optional<User> user = userService.findById(id);
		if (user==null) {
			return new ResponseEntity<Object>(
					new CustomErrorType("Unable to delete. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}
