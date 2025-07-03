package com.example.controller;

import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Twiliosms;
import com.example.model.User;
import com.example.service.TwilioService;
import com.example.service.UserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.*;
import com.twilio.type.PhoneNumber;

import jakarta.servlet.http.HttpSession;

@Controller
public class TwilioController {

	@Value("${app.twilio.account-sid}")
	private String accountSid;
	
	@Value("${app.twilio.auth-token}")
	private String authToken;
	
	@Value("${app.twilio.from-number}")
	private String fromNumber;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TwilioService twilioService;
	
	public static final Logger logger = LoggerFactory.getLogger(TwilioController.class);
	
	
	@GetMapping("/home")
	public String showHome(HttpSession session, Model model) throws Exception {
		logger.info("Enter into showHome() method");
		return "home";
	}
	
	@PostMapping("/send")
	public String sendSms(@RequestParam("recipient") String recipient, @RequestParam("message") String message, HttpSession session, Model model) throws Exception {
//		logger.info("Enter into sendSms() method");
		
		String email = (String) session.getAttribute("email");
		if (email == null) {
			model.addAttribute("error", "Email not found");
			return "redirect:/login";  
		}
		User user = userService.findByEmail(email);
		if (user != null) {
			Twilio.init(accountSid, authToken);
			Message.creator(new PhoneNumber(recipient), new PhoneNumber(fromNumber), message).create();
			Twiliosms twilio = new Twiliosms();
			twilio.setRecipient(recipient);
			twilio.setMessage(message);
			twilio.setUser(user);
			twilioService.sendMessageSave(twilio);
			model.addAttribute("success", "Message Send successfully");
		} else {
			model.addAttribute("error", "Session disconnect.");
		}
		
		return "home";
	}
	

	@GetMapping("/lst")
	public String showLst(HttpSession session, Model model) {
		try {
			String email = (String) session.getAttribute("email");
			User user = userService.findByEmail(email);
			List<Twiliosms> messages = twilioService.findByUserIdMsg(user.getId());
			model.addAttribute("messages", messages);
		} catch (Exception e) {
			model.addAttribute("error", "Unexpected error occurred: " + e.getMessage());
		}
		return "lst";
	}

	
	@PostMapping("/goback")
	public String lst(HttpSession session, Model model) throws Exception {
		return "home";
	}
	
	@GetMapping("thisuser")
	public String showThisUser(HttpSession session, Model model) {
		try {
			String email = (String) session.getAttribute("email");
			User user = userService.findByEmail(email);
			model.addAttribute("user", user);
		} catch (Exception e) {
			model.addAttribute("error", "Unexpected error occurred: " + e.getMessage());
		}
		return "thisuser";
	}
	
	@GetMapping("alluser")
	public String showAllUser(HttpSession session, Model model) {
		try {
			String email = (String) session.getAttribute("email");
			List<User> user = userService.allUsers();
			model.addAttribute("alluser", user);
			List<Twiliosms> messages = twilioService.allUserIdMsg();
			model.addAttribute("allmessages", messages);
		} catch (Exception e) {
			model.addAttribute("error", "Unexpected error occurred: " + e.getMessage());
		}
		return "alluser";
	}
	
}
