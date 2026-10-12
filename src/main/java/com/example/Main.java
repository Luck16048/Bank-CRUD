package com.example;

import com.azure.storage.blob.BlobContainerClient;
import com.example.config.AzureConnectionConfig;
import com.example.config.DatabaseConnectionConfig;
import com.example.controller.AccountController;
import com.example.controller.CardController;
import com.example.controller.UserProfileController;

import com.example.repository.AccountRepository;
import com.example.repository.CardRepository;
import com.example.repository.UserProfileRepository;
import com.example.service.AccountService;
import com.example.service.CardService;
import com.example.service.PhotoStorageService;
import com.example.service.UserProfileService;
import io.javalin.Javalin;
import org.jooq.DSLContext;
import org.jooq.exception.IntegrityConstraintViolationException;

import java.util.Map;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {

        DatabaseConnectionConfig.migrate();

        DSLContext dsl = DatabaseConnectionConfig.getDSL();


        UserProfileRepository userProfileRepository = new UserProfileRepository(dsl);
        AccountRepository accountRepository = new AccountRepository(dsl);
        CardRepository cardRepository = new CardRepository(dsl);


        UserProfileService userProfileService = new UserProfileService(userProfileRepository);
        AccountService accountService = new AccountService(accountRepository, userProfileRepository);
        CardService cardService = new CardService(cardRepository, accountRepository);
        BlobContainerClient containerClient = AzureConnectionConfig.getContainerClient();
        PhotoStorageService photoStorageService = new PhotoStorageService(containerClient);


        UserProfileController userProfileController = new UserProfileController(userProfileService, photoStorageService);
        AccountController accountController = new AccountController(accountService);
        CardController cardController = new CardController(cardService);


        Javalin app = Javalin.create(config -> {

            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> {
                    rule.anyHost();
                });
            });

        });


        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400).json(Map.of("error", e.getMessage()));
        });

        app.exception(NoSuchElementException.class, (e, ctx) -> {
            ctx.status(404).json(Map.of("error", e.getMessage()));
        });

        app.exception(IntegrityConstraintViolationException.class, (e, ctx) -> {
            ctx.status(409).json(Map.of("error", "Data conflicts with existing records or violates a constraint"));
        });

        app.exception(IntegrityConstraintViolationException.class, (e, ctx) -> {
            System.out.println("Constraint error: " + e.getMessage());
            ctx.status(409).json(Map.of("error", "Data conflicts with existing records or violates a constraint"));
        });

        app.post("/users", userProfileController::create);
        app.get("/users", userProfileController::getAll);
        app.get("/users/{id}", userProfileController::getById);
        app.delete("/users/{id}", userProfileController::delete);

        app.post("/accounts", accountController::create);
        app.get("/accounts", accountController::getAll);
        app.get("/accounts/{id}", accountController::getById);
        app.get("/users/{userId}/accounts", accountController::getByUser);
        app.delete("/accounts/{id}", accountController::delete);


        app.post("/cards", cardController::create);
        app.get("/cards", cardController::getAll);
        app.get("/cards/{id}", cardController::getById);
        app.get("/accounts/{accountId}/card", cardController::getByAccount);

        app.delete("/cards/{id}", cardController::delete);


        app.start(8080);
    }
}