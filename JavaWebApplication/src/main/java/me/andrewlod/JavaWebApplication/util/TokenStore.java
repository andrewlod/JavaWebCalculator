package me.andrewlod.JavaWebApplication.util;

import java.util.HashMap;

import me.andrewlod.JavaWebApplication.models.User;

public class TokenStore {

	private static TokenStore instance = null;
	private HashMap<String, User> tokenMap;
	
	private TokenStore() {
		this.tokenMap = new HashMap<String, User>();
	}
	
	public static TokenStore getInstance() {
		if(instance == null) {
			instance = new TokenStore();
		}
		return instance;
	}
	
	public String generateToken(User userId) {
		String token = TokenUtil.generate();
		this.tokenMap.put(token, userId);
		return token;
	}
	
	public User getUserId(String token) {
		return this.tokenMap.get(token);
	}
	
}
