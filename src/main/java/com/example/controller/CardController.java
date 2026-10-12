package com.example.controller;

import com.example.entity.Card;
import com.example.service.CardService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    public void create(Context ctx) {
        Card card = ctx.bodyAsClass(Card.class);
        Long id = cardService.create(card);
        Card created = cardService.getById(id);
        ctx.status(HttpStatus.CREATED);
        ctx.json(created);
    }

    public void getAll(Context ctx) {
        ctx.json(cardService.getAll());
    }

    public void getById(Context ctx) {
        Long id = Long.parseLong(
                ctx.pathParam("id")
        );
        Card card = cardService.getById(id);

        if (card == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("Card not found");
            return;
        }

        ctx.json(card);
    }

    public void getByAccount(Context ctx) {
        Long accountId = Long.parseLong(
                ctx.pathParam("accountId")
        );
        Card card = cardService.getByAccountId(accountId);

        if (card == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("Card not found");
            return;
        }

        ctx.json(card);
    }

    public void delete(Context ctx) {
        Long id = Long.parseLong(
                ctx.pathParam("id")
        );
        Card card = cardService.getById(id);

        if (card == null) {
            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json("Card not found");
            return;
        }

        cardService.delete(id);
        ctx.status(HttpStatus.NO_CONTENT);
    }
}