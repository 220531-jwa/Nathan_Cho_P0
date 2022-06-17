package Services;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;

import dev.cho.model.account;
import dev.cho.repositories.AccountDAO;
import dev.cho.repositories.UserDAO;
import dev.cho.service.AccountService;
import dev.cho.service.ClientService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
	
	@InjectMocks
	private static AccountService accountService;
	
	@Mock
	private static AccountDAO mockAccountDao;
	
	@BeforeEach
	public void setUp() {
		mockAccountDao = mock(AccountDAO.class);
		accountService = new AccountService(mockAccountDao);
	}
	
	@Test 
	public void should_ReturnCreatedAccount() {
		account ex = new account( 99, 100D, "test");
		
		when(mockAccountDao.createAccount(ex, 1)).thenReturn(ex);
		
		assertEquals(ex, accountService.createAccount(ex, 1));
	}
	
	@Test
	public void should_returnByAccountNumber() {
		//given
		
		account ex = new account(100, 100D, "test");
		
		when(mockAccountDao.getAccountByAccountNumber(1, 100)).thenReturn(ex);
		
		assertEquals(ex, accountService.getAccountByAccountNumber(1, 100));
	}

	@Test
	public void should_returnAllAccounts() {
		//given
		List<account> testList = new ArrayList<>();
		testList.add(new account());
		testList.add(new account());
		testList.add(new account());
		testList.add(new account());
		
		//when
		when(mockAccountDao.getAccountsByID(1)).thenReturn(testList);
		
		//then
		try {
			assertEquals(testList, accountService.getAccountsById(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void should_updateAccount() {
		
	}
	
	@Test
	public void should_deleteAccount() {
		
	}
	
	@Test
	public void should_getAccountsWithGreaterQuery() {
		//given
				List<account> testList = new ArrayList<>();
				testList.add(new account());
				testList.add(new account());
				testList.add(new account());
				testList.add(new account());
				
				//when
				when(mockAccountDao.getAccountsWithGreaterQuery(1, 100)).thenReturn(testList);
				
				//then
				try {
					assertEquals(testList, accountService.getAccountsWithGreaterQuery(1, 100));
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	
	@Test
	public void should_getAccountsWithLessQuery() {
		//given
				List<account> testList = new ArrayList<>();
				testList.add(new account());
				testList.add(new account());
				testList.add(new account());
				testList.add(new account());
				
				//when
				when(mockAccountDao.getAccountsWithLessQuery(1, 100)).thenReturn(testList);
				
				//then
				try {
					assertEquals(testList, accountService.getAccountsWithLessQuery(1, 100));
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

	@Test
	public void should_getAccountsWithBothQuery() {
		//given
				List<account> testList = new ArrayList<>();
				testList.add(new account());
				testList.add(new account());
				testList.add(new account());
				testList.add(new account());
				
				//when
				when(mockAccountDao.getAccountsWithBothQuery(1, 1, 1)).thenReturn(testList);
				
				//then
				try {
					assertEquals(testList, accountService.getAccountsWithBothQuery(1,1,1));
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

	@Test
	public void should_giveValueOnWithdraw() {
		//given
		double testVal = 111D;
		
		when(mockAccountDao.accountWithdraw(1, 1, testVal, 1000D)).thenReturn(testVal);
		when(mockAccountDao.getAccountByAccountNumber(1, 1)).thenReturn(new account(1,1000D,"test"));
		
		assertEquals(testVal, accountService.accountWithdraw(1, 1, testVal));
	}
	
	@Test
	public void should_giveValueOnDeposit() {
		double testVal = 1D;
		
		when(mockAccountDao.accountDeposit(1, 1, testVal, 0D)).thenReturn(testVal);
		when(mockAccountDao.getAccountByAccountNumber(1, 1)).thenReturn(new account(1,0D,"test"));
		assertEquals(testVal, accountService.accountDeposit(1, 1, testVal));
		
	}
}

