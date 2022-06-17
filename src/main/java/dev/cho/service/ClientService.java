package dev.cho.service;

import java.util.List;

import dev.cho.model.client;
import dev.cho.repositories.UserDAO;

public class ClientService {
	
	private static UserDAO userDao;
	
	public ClientService(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public List<client> getAllClients(){
		List<client> clientList = userDao.getAllClients();
		return clientList;
	}
	public client createClient() {
		client defaultParams = new client(0, "default", "default");
		client createdClient = userDao.createClient(defaultParams);
		return createdClient;
	}
	
	public client createClient(client c) {
		client createdClient = userDao.createClient(c);
		return createdClient;
	}

	public client getClientById(int id) throws Exception {
		
		client c =  userDao.getClientByID(id);
		
		
		return c;
	}

	public void deleteClient(int id) {
		userDao.deleteClient(id);
	}

	public void updateClient(int c, client cUpdate) {
		userDao.updateClient(c, cUpdate);
	}
}
