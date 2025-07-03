package com.example.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	public static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public User findByEmail(String email) throws Exception {
		logger.info("Enter into findByEmail() method");
		
		List<User> users = entityManager.createQuery("from User where email = :email", User.class)
				.setParameter("email", email).getResultList();
		return users.isEmpty() ? null : users.get(0);
	}
	
	public void registerSave(User user) throws Exception {
		logger.info("Enter into registerSave() method");
		entityManager.persist(user);
	}
	
	public void registerUpdate(User user) throws Exception {
		logger.info("Enter into registerUpdate() method");
		entityManager.merge(user);
	}
	
	public List<User> allUsers() throws Exception {
		logger.info("Enter into all Users");
		List<User> users = entityManager.createQuery("from User", User.class).getResultList();
		return users.isEmpty() ? null : users;
	} 
}
