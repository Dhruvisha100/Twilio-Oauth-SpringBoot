package com.example.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.model.User;

@Service
public class UserServiceImpl implements UserService {

	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	public User findByEmail(String email) throws Exception {
		logger.info("Enter into findByEmail() method");
		return userDao.findByEmail(email);
	}

	public void registerSave(User user) throws Exception {
		logger.info("Enter into registerSave() method");
		userDao.registerSave(user);
	}

	public void registerUpdate(User usesr) throws Exception {
		logger.info("Enter into registerUpdate() method");
		userDao.registerUpdate(usesr);
	}

	public List<User> allUsers() throws Exception{
		logger.info("Enter into allUsers() method");
		return userDao.allUsers();
	}
}
