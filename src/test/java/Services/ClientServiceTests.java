package Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import dev.cho.model.client;
import dev.cho.repositories.UserDAO;
import dev.cho.service.ClientService;

@ExtendWith(MockitoExtension.class) // got rid of the null pointer exception
public class ClientServiceTests {
	
	@InjectMocks
	private static ClientService clientService;
	
	@Mock
	private static UserDAO mockUserDao;

	@Test
	public void should_ReturnAllClients(){
		//given
		List<client> clientsMock = new ArrayList<>();
		clientsMock.add(new client(1, "joesmith", "testword1"));
		clientsMock.add(new client(2, "johncena", "testpa543ssword1"));
		clientsMock.add(new client(3, "janedoe", "testpasstffdgword1"));
		
		
		//when - clientService's getAllClients method is called
		when(mockUserDao.getAllClients()).thenReturn(clientsMock);
		//There's a null pointer exception above. idk yet
		
		
		//then - it should give all users.
		assertEquals(clientsMock,clientService.getAllClients());
		//assertEquals(expected, actual);
	}
	
	@Test
	public void should_CreateDefaultClient() {
		//given
		client example = new client();
		
		when(mockUserDao.createClient(example)).thenReturn(example);
		
		assertEquals(example, clientService.createClient(example));
	
	}
	
	@Test
	public void should_returnClientById() throws Exception {
		//given
		client ex = new client(6, "username", "password");
		//when
		
		when(mockUserDao.getClientByID(6)).thenReturn(ex);
		//then
		
		assertEquals(ex, clientService.getClientById(6));
		
	}
	
	@Test
	public void should_deleteClient() {
		//do I need to write a test for a method with no return?
	}
	
	@Test
	public void should_updateClient() {
		//same here
	}
}
