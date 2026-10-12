package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public void create(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Long id = accountService.create(account);
        Account created = accountService.getById(id);

        ctx.status(HttpStatus.CREATED);
        ctx.json(created);
    }

    public void getAll(Context ctx) {
        ctx.json(accountService.getAll());
    }

    public void getById(Context ctx) {
        Long id = Long.parseLong(
                ctx.pathParam("id")
        );
        Account account = accountService.getById(id);

        if (account == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("Account not found");
            return;
        }

        ctx.json(account);
    }

    public void getByUser(Context ctx) {
        Long userId = Long.parseLong(
                ctx.pathParam("userId")
        );

        ctx.json(accountService.getByUserProfileId(userId)
        );
    }

    public void delete(Context ctx) {
        Long id = Long.parseLong(
                ctx.pathParam("id")
        );
        Account account = accountService.getById(id);

        if (account == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("Account not found");
            return;
        }

        accountService.delete(id);
        ctx.status(HttpStatus.NO_CONTENT);
    }
}