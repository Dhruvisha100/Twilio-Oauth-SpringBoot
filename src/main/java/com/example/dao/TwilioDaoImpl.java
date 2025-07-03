package com.example.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.model.Twiliosms;
//import com.twilio.Twilio;

//import com.twilio.Twilio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class TwilioDaoImpl implements TwilioDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public static final Logger logger = LoggerFactory.getLogger(TwilioDaoImpl.class);

	@Override
	public List<Twiliosms> findByUserIdMsg(Long userId) throws Exception {
		logger.info("Enter into findByUserIdMsg() method");
		return entityManager.createQuery("FROM Twiliosms t WHERE t.user.id = :userId",  Twiliosms.class)
				.setParameter("userId", userId).getResultList();
	}
	
	public void sendMessageSave(Twiliosms sms) throws Exception{
		logger.info("Enter into sendMessageSave() method");
		entityManager.persist(sms);
	}
	
	@Override
	public List<Twiliosms> allUserIdMsg() throws Exception {
		logger.info("Enter into findByUserIdMsg() method");
		return entityManager.createQuery("FROM Twiliosms t",  Twiliosms.class)
				.getResultList();
	}
}
