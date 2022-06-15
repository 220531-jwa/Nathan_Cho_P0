package dev.cho.service;

import java.util.List;

import dev.cho.model.account;
import dev.cho.model.client;
import dev.cho.repositories.AccountDAO;

public class AccountService {
	private static AccountDAO accountDao = new AccountDAO();
	
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

	public void getAccountsWithQuery() {
		
	}
}
