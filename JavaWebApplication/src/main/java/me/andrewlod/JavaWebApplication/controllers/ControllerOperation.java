package me.andrewlod.JavaWebApplication.controllers;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import me.andrewlod.JavaWebApplication.models.Operation;
import me.andrewlod.JavaWebApplication.models.User;
import me.andrewlod.JavaWebApplication.models.UserAuth;
import me.andrewlod.JavaWebApplication.util.TokenStore;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Controller
public class ControllerOperation {

	@Autowired
	private SessionFactory sessionFactory;
	
	@PostMapping("/operation")
	public String operation(@RequestParam(name="login", required=true) String login, @RequestParam(name="password", required=true) String password, Model model) {
		
		Session session = sessionFactory.openSession();
		
		UserAuth userAuth = session.find(UserAuth.class, login);
		if(!userAuth.verifyPassword(password))
			return "error";
		
		User user = userAuth.getUser();
		
		String token = TokenStore.getInstance().generateToken(user);
		model.addAttribute("token", token);
		model.addAttribute("name", user.getName());
		
		return "operation";
	}

	@PostMapping("/result")
	public String result(@RequestParam(name="token", required=true) String token, @RequestParam(name="expression", required=true) String expression, Model model) {
		
		User userId = TokenStore.getInstance().getUserId(token);
		if(userId == null)
			return "error";
		
		double result;
		try {
			Expression _expression = new ExpressionBuilder(expression).build();
			result = _expression.evaluate();
		}catch(Exception e) {
			return "error";
		}
		model.addAttribute("result", result);

		Session session = sessionFactory.openSession();
		
		Criteria crit = session.createCriteria(Operation.class);
		crit.add(Restrictions.eq("user_id", userId));
		List<Operation> results = crit.list();
		
		model.addAttribute("operations", results);
		
		session.beginTransaction();
		Operation operation = new Operation(expression, result, userId);
		session.save(operation);
		session.getTransaction().commit();
		session.close();
		
		return "result";
	}
	
}
