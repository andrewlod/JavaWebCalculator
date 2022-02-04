package me.andrewlod.JavaWebApplication.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Operation implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String expression;
	private Double result;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user_id;
	
	private Operation() {
		
	}
	
	public Operation(String expression, Double result, User user_id) {
		this.expression = expression;
		this.result = result;
		this.user_id = user_id;
	}
	
	public String getExpression() {
		return this.expression;
	}
	
	public Double getResult() {
		return this.result;
	}
	
	public User getUser() {
		return this.user_id;
	}
	
	

}
