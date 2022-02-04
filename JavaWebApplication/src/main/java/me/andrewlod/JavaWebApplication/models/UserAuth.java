package me.andrewlod.JavaWebApplication.models;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import me.andrewlod.JavaWebApplication.util.EnvironmentUtil;
import me.andrewlod.JavaWebApplication.util.HashUtil;

@Entity
public class UserAuth implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	private String password_hash;
	
	@OneToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user_id;
	
	private UserAuth() {
		
	}
	
	public UserAuth(String username, String password, User user) {
		this.username = username;
		
		this.password_hash = HashUtil.hash(password);
		
		this.user_id = user;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPasswordHash() {
		return this.password_hash;
	}
	
	public boolean verifyPassword(String password) {
		return HashUtil.hash(password).equals(this.password_hash);
	}
	
	public User getUser() {
		return this.user_id;
	}

}
