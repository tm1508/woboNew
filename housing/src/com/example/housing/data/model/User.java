package com.example.housing.data.model;

import javax.persistence.*;

@Entity
@Table(name="user")
@NamedQueries(@NamedQuery(name = "firstUser", query = "SELECT u FROM User u"))
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idUser;
	private String firstname;
	private String lastname;
	private String email;
	private String mobile;
	private String password;
	private String dhMail;
	private int accessLevel;
	
	public long getIdUser() {
		return idUser;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDhMail() {
		return dhMail;
	}
	public void setDhMail(String dhMail) {
		this.dhMail = dhMail;
	}
	public int getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

}
