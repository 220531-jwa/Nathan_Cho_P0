package dev.cho.controller;

import java.util.List;

import dev.cho.model.client;
import dev.cho.repositories.UserDAO;
import dev.cho.service.ClientService;
import io.javalin.http.Context;

public class ClientController {
	private static ClientService cs = new ClientService(new UserDAO());
	
	
	
	public static void getAllClients(Context ctx) {
		ctx.status(200);
		List<client> clients = cs.getAllClients();
		ctx.json(clients);
	}
	
	public static void createNewClient(Context ctx) {
		
		String paramCheck = ctx.body();
		
		if(paramCheck.contains("username") && paramCheck.contains("password")) {
			
		ctx.status(201);
		client userFromRequestBody = ctx.bodyAsClass(client.class);
		client c = cs.createClient(userFromRequestBody); // unmarshalling
		ctx.json(c);
		}
		else {
			ctx.status(201);
			client d = cs.createClient();
			ctx.json(d);
		}
		
	}
	
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
		
		String body = ctx.body();
		if(!body.contains("userId") || !body.contains("username") || !body.contains("password")) {
			ctx.status(400);
			ctx.result("Unauthorized format: please add parameters for client");
		}

		else {
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
}
