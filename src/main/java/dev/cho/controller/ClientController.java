package dev.cho.controller;

import java.util.List;

import dev.cho.model.client;
import dev.cho.service.ClientService;
import io.javalin.http.Context;

public class ClientController {
	private static ClientService cs = new ClientService();
	
	
	
	public static void getAllClients(Context ctx) {
		ctx.status(200);
		List<client> clients = cs.getAllClients();
		ctx.json(clients);
	}
	
	public static void createNewClient(Context ctx) {
		ctx.status(201);
		client userFromRequestBody = ctx.bodyAsClass(client.class);
		client c = cs.createClient(userFromRequestBody); // unmarshalling
		ctx.json(c);
	}
	
	/*
	 * public static void getUserById(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		User u = null;
		try {
			u = us.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ctx.status(200);
		ctx.json(u);
	}
	*/
	public static void getClientById(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		client c = null;
		try {
			c = cs.getClientById(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(c != null) {
		ctx.status(200);
		ctx.json(c);
		}
		else {
			ctx.status(404);
			ctx.result("User Not Found");
		}
	}

	public static void deleteClient(Context ctx){
		
		int id = Integer.parseInt(ctx.pathParam("id"));
		
		try {
			if(cs.getClientById(id) != null) {
				ctx.status(205);
				cs.deleteClient(id);
				ctx.result("Client with id: " + id + " has been deleted.");
			}
			else {
				ctx.status(404);
				ctx.result("User Not Found, Check ID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void updateClient(Context ctx) {
		
		client cUpdate = ctx.bodyAsClass(client.class);
		int id = Integer.parseInt(ctx.pathParam("id"));
		
		try {
			if(cs.getClientById(id) != null) {
				cs.updateClient(id, cUpdate);
				ctx.result("Update Successful");
				ctx.status(200);
			}
			else {
				ctx.status(404);
				ctx.result("User Not Found, Check ID");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
