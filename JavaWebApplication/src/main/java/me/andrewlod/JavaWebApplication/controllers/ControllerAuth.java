package me.andrewlod.JavaWebApplication.controllers;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import me.andrewlod.JavaWebApplication.models.User;
import me.andrewlod.JavaWebApplication.models.UserAuth;
import me.andrewlod.JavaWebApplication.util.TokenStore;
import me.andrewlod.JavaWebApplication.util.TokenUtil;

@Controller
public class ControllerAuth {
	
	@Autowired
	private SessionFactory sessionFactory;

	@RequestMapping("/auth")
	public String auth(Model model) {
		return "auth";
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		return "register";
	}
	
	@PostMapping("/register/submit")
	public String submitRegister(@RequestParam(name="login", required=true) String login, @RequestParam(name="password", required=true) String password, @RequestParam(name="name", required=true) String name, Model model) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User user = new User(name);
		UserAuth userAuth = new UserAuth(login, password, user);
		session.save(user);
		session.save(userAuth);
		session.getTransaction().commit();
		session.close();
		
		return "redirect:/auth";
	}
	
}
