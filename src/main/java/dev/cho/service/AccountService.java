package dev.cho.service;

import java.util.ArrayList;
import java.util.List;

import dev.cho.model.account;
import dev.cho.model.client;
import dev.cho.repositories.AccountDAO;

public class AccountService {
	private static AccountDAO accountDao = new AccountDAO();
	
	public account createAccount(int id) {
		account defaultParams = new account(0, 0D, "checking");
		account createdAccount= accountDao.createAccount(defaultParams, id);
		return createdAccount;
	}
	
	public account createAccount(account a, int id) {
		account createdAccount = accountDao.createAccount(a, id);
		return createdAccount;
	}
	
	public account getAccountByAccountNumber(int id, int accNum) {
		
		account a = accountDao.getAccountByAccountNumber(id, accNum);
		return a;
	}
	
	public List<account> getAccountsById(int id) throws Exception {
		
		List<account> accounts =  accountDao.getAccountsByID(id);
		
		
		return accounts;
	}
	
	public void updateAccount(int id, account aUpdate) {
		accountDao.updateAccount(id, aUpdate);
	}
	
	public void deleteAccountByAccountNumber(int id, int accNum) {
		accountDao.deleteAccountByAccountNumber(id, accNum);
	}

	public List<account> getAccountsWithGreaterQuery(int id, double amount) {
		//List<account> accounts = accountDao.getAccountsWithGreaterQuery(id, queries);
		
		// accountDao.getAccountsWithLessQuery(id, queries);
		
		List<account> accounts = new ArrayList<>();
		if(accountDao.getAccountsWithGreaterQuery(id, amount) != null) {
			for(account elem: accountDao.getAccountsWithGreaterQuery(id, amount)) {
			accounts.add(elem);
			}
		}
		return accounts;
	}
	
	public List<account> getAccountsWithLessQuery(int id, double amount){
		
		List<account> accounts = new ArrayList<>();
		if(accountDao.getAccountsWithLessQuery(id, amount) != null) {
			for(account elem: accountDao.getAccountsWithLessQuery(id, amount)) {
				accounts.add(elem);
			}
		}
		
		return accounts;
	}
	
	public List<account> getAccountsWithBothQuery(int id, double ammountG, double ammountL){
		
		List<account> accounts = new ArrayList<>();
		if(accountDao.getAccountsWithBothQuery(id, ammountG, ammountL) != null) {
			for(account elem: accountDao.getAccountsWithBothQuery(id, ammountG, ammountL)) {
				accounts.add(elem);
			}
		}
	
			return accounts;
	}

	public double accountWithdraw(int id, int accNum, double ammount) {
		
		account inQuestion = accountDao.getAccountByAccountNumber(id, accNum);
		if(inQuestion.getBalance() > ammount) {
			accountDao.accountWithdraw(id, accNum, ammount, inQuestion.getBalance());
		}
		else return -1;
		
		double newBal = accountDao.getAccountByAccountNumber(id, accNum).getBalance();
		
		return newBal;
	}
	
	public double accountDeposit(int id, int accNum, double ammount) {
		
		accountDao.accountDeposit(id, accNum, ammount, accountDao.getAccountByAccountNumber(id, accNum).getBalance());
		
		double newBal = accountDao.getAccountByAccountNumber(id, accNum).getBalance();
		
		return newBal;
	}
	
}
