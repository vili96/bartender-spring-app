package com.spring.bartenderapp.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.bartenderapp.WebSecurityConfig;
import com.spring.bartenderapp.models.User;
import com.spring.bartenderapp.repositories.IUserRepo;

@RestController
public class LoginController {

	private IUserRepo userRepo;
	private WebSecurityConfig webSecurityConfig;

	public LoginController(IUserRepo userRepo, WebSecurityConfig webSecurityConfig) {
		this.userRepo = userRepo;
		this.webSecurityConfig = webSecurityConfig;
	}

	@PostMapping(path = "/register")
	public User register(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "repeatPassword") String repeatPassword) {

		if (password.equals(repeatPassword)) {
			User user = new User(username, hashPassword(password));

			return userRepo.saveAndFlush(user);
		} else {
			return null;
		}
	}

	@PostMapping(path = "/login")
	public String login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, HttpSession session) {

		User user = userRepo.findUserByUsernameAndPassword(username, hashPassword(password));

		if (user != null) {
			session.setAttribute("user", user);

			UserDetails userDetails = webSecurityConfig.userDetailsService().loadUserByUsername(user.getUsername());

			if (userDetails != null) {
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
						userDetails.getPassword(), userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);

				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
						.currentRequestAttributes();

				HttpSession httpSession = attr.getRequest().getSession(true);
				httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			}

			return "index.html";
		} else {
			return "error.html";
		}

	}
	
	@PostMapping(path="/logout")
	public ResponseEntity<Boolean> logout(HttpSession session){
		
		User user = (User) session.getAttribute("user");
		if (user==null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		session.invalidate();
		return new ResponseEntity<>(true,HttpStatus.OK);
	}

	@GetMapping(path = "/profile")
	public ResponseEntity<User> getProfile(HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {

			Optional<User> userFromDb = userRepo.findById(user.getId());

			if (userFromDb.isPresent()) {
				return new ResponseEntity<>(userFromDb.get(), HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	}

	@GetMapping(path = "/get_logged_user")
	public ResponseEntity<Integer> getLoggedUser(HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {
			return new ResponseEntity<>(user.getId(), HttpStatus.OK);
		}

		return new ResponseEntity<>(0, HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping(path = "/get_logged_username")
	public String getLoggedUsername(HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {
			return user.getUsername();
		}

		return null;
	}


	private String hashPassword(String text) {

		StringBuilder sb = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(text.getBytes());

			byte[] bytes = md.digest();

			for (int i = 0; i < bytes.length; i++) {
				sb.append((char) bytes[i]);
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return sb.toString();

	}

}
