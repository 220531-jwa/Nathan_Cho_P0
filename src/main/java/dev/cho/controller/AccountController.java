package dev.cho.controller;

import java.util.ArrayList;
import java.util.List;

import dev.cho.model.account;
import dev.cho.model.client;
import dev.cho.service.AccountService;
import dev.cho.service.ClientService;
import io.javalin.http.Context;

public class AccountController {
	private static AccountService as = new AccountService();
	private static ClientService cs = new ClientService();
	
	public static void createNewAccount(Context ctx) {
		ctx.status(201);
		int id = Integer.parseInt(ctx.pathParam("id"));
		
		account userFromRequestBody = ctx.bodyAsClass(account.class);
		account a = as.createAccount(userFromRequestBody, id); // unmarshalling
		ctx.result("New account for client ID: " + id + " has been created");
		ctx.json(a);
		
	}
	
	public static void getAccountByAccountNumber(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accNum = Integer.parseInt(ctx.pathParam("account_number"));
		account a = null;
		
		try {
			 a = as.getAccountByAccountNumber(id, accNum);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(a != null) {
		ctx.status(200);
		ctx.json(a);
		}
		else {
			ctx.status(404);
			ctx.result("No Accounts with that Account Number found. Check Account Number?");
		}
	}
	
	public static void getAccountsByID(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		List<account> accounts = new ArrayList<account>();
		try {
			 accounts = as.getAccountsById(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(accounts != null) {
		ctx.status(200);
		ctx.json(accounts);
		}
		else {
			ctx.status(404);
			ctx.result("No Accounts Found");
		}
	}

	public static void updateAccount(Context ctx) {
		account aUpdate = ctx.bodyAsClass(account.class);
		int id = Integer.parseInt(ctx.pathParam("id"));
		
		try {
			if( cs.getClientById(id) != null){
				as.updateAccount(id, aUpdate);
				ctx.result("Update of Account with account_number: " 
						+ ctx.pathParam("account_number") + " successful.");
				ctx.status(200);
			}
			else {
				ctx.status(404);
				ctx.result("Account Not Found. Check User ID or Account Number");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
//	public static void getAccountsWithQuery(Context ctx) {
//		int id = Integer.parseInt(ctx.pathParam("id"));
//		List<account> accounts = new ArrayList<account>();
//		
//		try {
//			accounts = as.getAccountsWithQuery(ctx.queryParam(null)); //needs work here
//			
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		if(accounts != null) {
//			ctx.status(200);
//			ctx.json(accounts);
//		}
//		else {
//			ctx.status(404);
//			ctx.result("No Accounts Found with the given conditions");
//		}
//	}
	
	public static void deleteAccountByAccountNumber(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accNum = Integer.parseInt(ctx.pathParam("account_number"));
		
		try {
			if(as.getAccountByAccountNumber(id, accNum) != null) {
				ctx.status(205);
				as.deleteAccountByAccountNumber(id, accNum);
				ctx.result("Account with Account Number: " + accNum + 
						" bound to user id: " + id + " has been deleted.");
			}
			else {
				ctx.status(404);
				ctx.result("User Not Found, Check ID or Account Number");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
