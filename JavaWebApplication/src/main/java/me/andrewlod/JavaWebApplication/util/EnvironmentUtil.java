package me.andrewlod.JavaWebApplication.util;

import java.util.Map;

public class EnvironmentUtil {

	private static EnvironmentUtil instance = null;
	
	private Map<String, String> env;
	
	private EnvironmentUtil() {
		this.env = System.getenv();
	}
	
	public static EnvironmentUtil getInstance() {
		if(instance == null) {
			instance = new EnvironmentUtil();
		}
		return instance;
	}
	
	public String getKey(String key) {
		return this.env.get(key);
	}
}
