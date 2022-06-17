package dev.cho.controller;

import java.util.ArrayList;
import java.util.List;

import dev.cho.model.account;
import dev.cho.model.client;
import dev.cho.repositories.UserDAO;
import dev.cho.service.AccountService;
import dev.cho.service.ClientService;
import io.javalin.http.Context;

public class AccountController {
	private static AccountService as = new AccountService();
	private static ClientService cs = new ClientService(new UserDAO());
	
	public static void createNewAccount(Context ctx) {
		
		String paramCheck = ctx.body();
		int id = Integer.parseInt(ctx.pathParam("id"));
		
		if(paramCheck.contains("balance") && paramCheck.contains("type")) {
		ctx.status(201);
		
		
		account userFromRequestBody = ctx.bodyAsClass(account.class);
		account a = as.createAccount(userFromRequestBody, id); // unmarshalling
		ctx.result("New account for client ID: " + id + " has been created");
		ctx.json(a);
		
		}
		else {
			ctx.status(201);
			account a = as.createAccount(id); // unmarshalling
			ctx.result("New Default Checking account for client ID: " + id + " has been created");
			ctx.json(a);
		}
		
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
		try {
			ctx.json(cs.getClientById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		//if there is a query parameter we send it to a different service.
		String param = ctx.queryParam("ammountGreaterThan");
		String param2 = ctx.queryParam("ammountLessThan");
		double greaterVal = 0;
		double lessVal = 0;
		
		if(param!=null) {
		greaterVal = Double.parseDouble(param);
		}
		if(param2!=null) {
		lessVal = Double.parseDouble(param2);
		}
		
		if(param != null && param2 != null) {
			try{
				accounts = as.getAccountsWithBothQuery(id, greaterVal, lessVal);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(param != null) {
			try {
				accounts = as.getAccountsWithGreaterQuery(id, greaterVal);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(param2 != null) {
			try{
				accounts = as.getAccountsWithLessQuery(id, lessVal);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else try {
			 accounts = as.getAccountsById(id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(accounts != null && accounts.size() != 0) {
		ctx.status(200);
		try {
			ctx.json(cs.getClientById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ctx.json(accounts);
		}
		else {
			ctx.status(404);
			ctx.result("No Accounts Found");
		}
	}

	public static void updateAccount(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accNum = Integer.parseInt(ctx.pathParam("account_number"));
		
		String body = ctx.body();
		if(!body.contains("balance") || !body.contains("type") || !body.contains("accountNumber")) {
			ctx.status(400);
			ctx.result("Unauthorized format: please add parameters for client");
		}
		else {
		account aUpdate = ctx.bodyAsClass(account.class);
		try {
			if( as.getAccountByAccountNumber(id, accNum) != null){
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
		
	}
	
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

	public static void accountTransaction(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accNum = Integer.parseInt(ctx.pathParam("account_number"));
		String toDo = ctx.body();
		double xammount = -1D;
		if(!toDo.replaceAll( "[^0-9.]", "").isBlank() ) {
		xammount = Double.parseDouble(toDo.replaceAll( "[^0-9.]", ""));
		}
		double updatedBal;
	if(xammount != -1D) {
		try {
			if(as.getAccountByAccountNumber(id, accNum) != null) {
				if(toDo.contains("withdraw")) {  //test for withdraw vs. deposit.
					updatedBal = as.accountWithdraw(id, accNum, xammount);
					if(updatedBal != -1) {
						ctx.result("Your transaction was successful. Your new balance: " + updatedBal);
						ctx.status(200);
					}
					else {
						ctx.result("You do not have the funds.");
						ctx.status(422);
					}
				}
				else if(toDo.contains("deposit")) { //withdraw tested first then deposit tested.
					updatedBal = as.accountDeposit(id, accNum, xammount);
					
					ctx.result("Your transaction was successful. Your new balance: " + updatedBal);
					ctx.status(200);
				}
				
			} else {
				ctx.status(404);
				ctx.result("Could not find the account, check User ID or Account Number");
			}
		}	
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		else {
			ctx.status(400);
			ctx.result("Specify Withdrawal or Deposit and ammount numerically");
		}
	}

	public static void accountTransfer(Context ctx) {
		int id = Integer.parseInt(ctx.pathParam("id"));
		int accNum = Integer.parseInt(ctx.pathParam("account_number"));
		int trgAccNum = Integer.parseInt(ctx.pathParam("target_account_number"));
		double currentBal = Double.MAX_VALUE;
		boolean bothReal = as.getAccountByAccountNumber(id, accNum)!= null //checking both accounts existence
				&& as.getAccountByAccountNumber(id, trgAccNum)!= null;
		double xammount = Double.parseDouble(ctx.body().replaceAll("[^0-9.]", ""));
		if(as.getAccountByAccountNumber(id, accNum) != null) {
		currentBal = as.getAccountByAccountNumber(id, accNum).getBalance();
		}
		
		if(bothReal && currentBal > xammount) {
		as.accountWithdraw(id, accNum, xammount);
		as.accountDeposit(id, trgAccNum, xammount);
		ctx.status(200);
		ctx.result("Transfer between accounts with account number: " 
				+ accNum + " to " + trgAccNum + " completed" + "\n" + "new balance for account: " 
				+ accNum + " = " + as.getAccountByAccountNumber(id, accNum).getBalance()
				+ "\n" + "new balance for account: " 
				+ trgAccNum + " = " + as.getAccountByAccountNumber(id, trgAccNum).getBalance());
		}
		else if(xammount > currentBal) {
			ctx.status(422);
			ctx.result("Insufficient funds");
		}
		else {
			ctx.status(404);
			ctx.result("Account not found. Check User ID, or Account Number.");
		}
	}
}
