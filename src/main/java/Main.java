import java.nio.file.Path;

import javax.management.Query;

import dev.cho.model.client;
import dev.cho.repositories.AccountDAO;
import dev.cho.repositories.UserDAO;
import dev.cho.model.account;
import dev.cho.controller.AccountController;
import dev.cho.controller.ClientController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Main {
	public static void main(String[] args) {
		
		UserDAO ud = new UserDAO();
		AccountDAO ad = new AccountDAO();
		
		Javalin app = Javalin.create();
		
		app.start(8081);
			
		app.get("/", ctx -> {
			ctx.status(200);
			ctx.result("Hello!");
		});
		
		app.routes(() -> {
			path("/clients", () -> {
				get(ClientController::getAllClients);
				post(ClientController::createNewClient);
				path("/{id}", () -> {
						get(ClientController::getClientById);
						delete(ClientController::deleteClient);
						put(ClientController::updateClient);
//						patch(ClientControler::updatePassword);
						path("/accounts", () -> {
							get(AccountController::getAccountsByID);
							post(AccountController::createNewAccount);
							path("/{account_number}", () -> {
								get(AccountController::getAccountByAccountNumber);
								delete(AccountController::deleteAccountByAccountNumber);
								put(AccountController::updateAccount);
							});
						});
				});
			});
			
		});
 		
		
		
		
		
		
		

	}



		
}

