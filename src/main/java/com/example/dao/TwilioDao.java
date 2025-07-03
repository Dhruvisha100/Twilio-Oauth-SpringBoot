package com.example.dao;

import java.util.List;

import com.example.model.Twiliosms;

//import com.twilio.Twilio;

public interface TwilioDao {

	public List<Twiliosms> findByUserIdMsg(Long userId) throws Exception;
	
	public void sendMessageSave(Twiliosms sms) throws Exception;
	
	public List<Twiliosms> allUserIdMsg() throws Exception;
}
