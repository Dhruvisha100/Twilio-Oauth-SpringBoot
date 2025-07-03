package com.example.service;

import java.util.List;

import com.example.model.User;

public interface UserService {

	public User findByEmail(String email) throws Exception;
	
	public void registerSave(User user) throws Exception;
	
	public void registerUpdate(User usesr) throws Exception;
	
	public List<User> allUsers() throws Exception;
	
}
