package com.example.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.User;
import com.example.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired 
	private UserService userService;
	
	
	@GetMapping("/")
	public String loginContext() {
		logger.info("Enter into loginContext() method");
		return "login";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		logger.info("Enter into loginPage() method");
		return "login";
	}
	
//	@GetMapping("/home")
//	public String home() {
//		logger.info("Enter into home() method");
//		return "home";
//	}
	
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) throws Exception {
		logger.info("Enter into post login() method");
		User user = userService.findByEmail(username);
		if (user == null) {
			model.addAttribute("error", "If not found data");
			return "login";
		} else {
			if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
				session.setAttribute("email", user.getEmail());
				model.addAttribute("success", "Login success");
				return "home";
			} else {
				model.addAttribute("error", "If wrong data");
				return "login";
			}
		}
		
	}
	
	@GetMapping("/register")
	public String showRegister() {
		logger.info("Enter into showRegister() method");
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) throws Exception {
		logger.info("Enter into register() method");
		
		User user = userService.findByEmail(email);
		if (user != null) {
			model.addAttribute("error", "If alreay register.");
			session.setAttribute("email", email);
			return "login";
		} else {
			user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setPasswordSet(true);
			userService.registerSave(user);
			session.setAttribute("email", email);
			model.addAttribute("success","Successfully save with google");
			return "login";
		}
	}
	
	@GetMapping("/oauth2/success")
	public String googleRegister(OAuth2AuthenticationToken token, HttpSession session, Model model) throws Exception {
		logger.info("Enter into googleRegister() method");
		
		String email = token.getPrincipal().getAttribute("email");
		String name = token.getPrincipal().getAttribute("name");
		
		User user = userService.findByEmail(email);
		if (user != null) {
			model.addAttribute("error", "If alreay register.");
			session.setAttribute("email", email);
			return "login";
		} else {
			user = new User();
			user.setEmail(email);
			user.setName(name);
			user.setPasswordSet(false);
			userService.registerSave(user);
			session.setAttribute("email", email);
			model.addAttribute("success","Successfully save with google");
			return "set-password";
		}
	}
	
	@GetMapping("/set-password")
	public String showSetPassword() {
		logger.info("Enter into showSetPassword() method");
		return "set-password";
	}
	
	@PostMapping("/set-password")
	public String savePassword(@RequestParam("password") String password, HttpSession session, Model model) throws Exception {
		logger.info("Enter into savePassword");
		
		String email = (String) session.getAttribute("email");
		User user = userService.findByEmail(email);
		if (user != null) {
			if (user.isPasswordSet()) {
				model.addAttribute("error", "Something when wrong.");
			} else {
				user.setPassword(password);
				user.setPasswordSet(true);
				userService.registerUpdate(user);
				model.addAttribute("success", "Set Password");
			}
		} else {
			model.addAttribute("error", "Not Found Try Again.");
		}
		
		return "login";
	}
	
	@GetMapping("/forgot-password")
	public String showForgotPassword() {
		logger.info("Enter into showForgotPassword() method");
		return "forgot-password";
	}
	
	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam("username") String username ,@RequestParam("password") String password, HttpSession session, Model model) throws Exception {
		
//		String email = (String) session.getAttribute("email");
		User user = userService.findByEmail(username);
		if (user != null) {
			user.setPassword(password);
			userService.registerUpdate(user);
			model.addAttribute("success", "Set Password");
		} else {
			model.addAttribute("error", "Not Found Try Again.");
		}
		
		
		return "login";
	}
	
	
}
