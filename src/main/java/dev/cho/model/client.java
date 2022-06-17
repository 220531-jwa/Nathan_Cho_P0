package dev.cho.model;

public class client {
	private String username;
	private String password;
	private int userId;
	
	
	public client() { //default client
		super();
		this.userId = 0;
		this.username = "default";
		this.password = "default";
	}
	
	public client(int i, String string, String string2) {
		super();
		this.userId = i;
		this.username = string;
		this.password = string2;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
