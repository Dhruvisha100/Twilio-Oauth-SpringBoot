package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.TwilioDao;
import com.example.model.Twiliosms;

@Service
public class TwilioServiceImpl implements TwilioService {

	@Autowired
	private TwilioDao twilioDao;
	
	public List<Twiliosms> findByUserIdMsg(Long userId) throws Exception {
		return twilioDao.findByUserIdMsg(userId);
	}
	
	public void sendMessageSave(Twiliosms sms) throws Exception {
		twilioDao.sendMessageSave(sms);
	}
	
	public List<Twiliosms> allUserIdMsg() throws Exception {
		return twilioDao.allUserIdMsg();
	}
}
