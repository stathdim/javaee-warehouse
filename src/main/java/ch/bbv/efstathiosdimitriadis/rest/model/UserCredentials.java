package ch.bbv.efstathiosdimitriadis.rest.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserCredentials implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4694699120329876567L;
	private String username;
	private String password;

	public UserCredentials() {

	}

	@JsonGetter("username")
	public String getUsername() {
		return username;
	}

	@JsonSetter("username")
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonGetter("password")
	public String getPassword() {
		return password;
	}

	@JsonSetter("password")
	public void setPassword(String password) {
		this.password = password;
	}

}
