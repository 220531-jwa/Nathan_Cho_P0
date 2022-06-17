package Services;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;

import dev.cho.model.account;
import dev.cho.repositories.AccountDAO;
import dev.cho.service.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
	@InjectMocks
	private static AccountService accountService;
	
	@Mock
	private static AccountDAO mockAccountDao;
	
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
}
