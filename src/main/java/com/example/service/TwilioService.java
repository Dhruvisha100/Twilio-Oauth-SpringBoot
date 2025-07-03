package com.example.service;

import java.util.List;

import com.example.model.Twiliosms;

public interface TwilioService {

	public List<Twiliosms> findByUserIdMsg(Long userId) throws Exception;
	
	public void sendMessageSave(Twiliosms sms) throws Exception;
	
	public List<Twiliosms> allUserIdMsg() throws Exception;
}
