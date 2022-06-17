package Repositories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.cho.model.account;
import dev.cho.repositories.AccountDAO;
import dev.cho.repositories.UserDAO;

public class AccountDAOTests {

	private static AccountDAO accountDao;
	
	@BeforeEach
	public void setUp() {
		accountDao = new AccountDAO();
	}
	
	@Test 
	public void NotNullWhen_createAccount(){
		assertNotNull(accountDao.createAccount(new account(), 1));
	}
	
	@Test 
	public void NullWhen_getAccByAccNumNoAccs(){
		assertNull(accountDao.getAccountByAccountNumber(1, 1));
	}
	
	@Test 
	public void NotNullWhen_GetAccByIdNoId(){
		assertNotNull(accountDao.getAccountsByID(1));
	}
	
	@Test 
	public void NotNullWhen_getGreaterQuery(){
		assertNotNull(accountDao.getAccountsWithGreaterQuery(3, 0D));
	}
	
	@Test 
	public void NotNullWhen_getLessQuery(){
		assertNotNull(accountDao.getAccountsWithLessQuery(3, 1000D));
	}
	
	@Test 
	public void NotNullWhen_getBothQuery(){
		assertNotNull(accountDao.getAccountsWithBothQuery(1, 0D, 1000D));
	}
	
	@Test 
	public void NotNullWhen_accountWithdraw(){
		assertNotNull(accountDao.accountWithdraw(1, 1, 1, 2));
	}
	
	@Test 
	public void NotNullWhen_accountDeposit(){
		assertNotNull(accountDao.accountDeposit(1, 1, 1, 1));
	}
}
