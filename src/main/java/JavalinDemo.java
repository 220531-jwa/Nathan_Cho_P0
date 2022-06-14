import java.nio.file.Path;

import dev.cho.model.client;
import dev.cho.model.account;

import dev.cho.controller.ClientController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;

import static io.javalin.apibuilder.ApiBuilder.*;


public class JavalinDemo {
	public static void main(String[] args) {
		
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
				});
			});
			
		});
 		
		
		
		
		
		
		

	}



		
}

