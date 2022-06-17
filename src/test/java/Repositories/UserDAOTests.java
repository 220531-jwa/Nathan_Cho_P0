package Repositories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import dev.cho.model.client;
import dev.cho.repositories.UserDAO;

@ExtendWith(MockitoExtension.class)
public class UserDAOTests {

	private static UserDAO userDao;
	
	@BeforeEach
	public void setUp() {
		userDao = new UserDAO();
	}
	
	@Test 
	public void NotNullWhen_CreatingClient(){
		assertNotNull(userDao.createClient(new client()));
	}
	
	@Test
	public void NotNullWhen_gettingClientList() {
		assertNotNull(userDao.getAllClients());
	}
	
	@Test
	public void NotNullWhen_getClientById() {
		assertNotNull(userDao.getClientByID(1));
	}
	@Test
	public void NullWhen_GetClientByUsernameNoMatch() {
		assertNull(userDao.getClientByUsername(""));
	}
}
