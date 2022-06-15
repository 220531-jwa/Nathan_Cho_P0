package dev.cho.model;

public class account {
	private int accountNumber;
	private double balance;
	private String type;
	
	public account() {
		super();
	}
	
	public account(int accNum, double balance, String type) {
		super();
		this.accountNumber = accNum;
		this.balance = balance;
		this.setType(type);
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber( int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setDouble(double balance) {
		this.balance = balance;
	}

	
	public String getType() {
		return type;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
}
