package com.xsushirollx.sushibyte.restaurantservice.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User  {

	@Id
	@Column(name = "id")
	Integer id;
	
	@Column(name = "user_role")
	Integer role;
	
	@Column(name = "username")
	String username;
	
	@Column(name = "email")
	String email;

	
	public User() {
		super();
	}

	public User(Integer id, Integer role) {
		super();
		this.id = id;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", role=" + role +  "]";
	}
	
	

}
