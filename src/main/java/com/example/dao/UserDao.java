package com.example.dao;

import java.util.List;

import com.example.model.User;

public interface UserDao {

	public User findByEmail(String email) throws Exception;

	public void registerSave(User usesr) throws Exception;

	public void registerUpdate(User usesr) throws Exception;

	public List<User> allUsers() throws Exception;

}
