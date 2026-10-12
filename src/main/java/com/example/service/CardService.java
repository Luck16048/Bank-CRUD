package com.example.service;

import com.example.entity.Card;
import com.example.repository.AccountRepository;
import com.example.repository.CardRepository;

import java.util.List;

public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public CardService(
            CardRepository cardRepository,
            AccountRepository accountRepository
    ) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    public Long create(Card card) {
        if (card.getNumber() == null ||
                !card.getNumber().matches("[0-9]{16}")) {

            throw new IllegalArgumentException(
                    "Card number must contain exactly 16 digits"
            );
        }

        if (card.getAccountId() == null) {
            throw new IllegalArgumentException(
                    "Account id is required"
            );
        }

        if (accountRepository.findById(card.getAccountId()) == null) {
            throw new IllegalArgumentException(
                    "Account not found"
            );
        }

        if (cardRepository.findByAccountId(card.getAccountId()) != null) {
            throw new IllegalArgumentException(
                    "This account already has a card"
            );
        }

        return cardRepository.create(card);
    }

    public Card getById(Long id) {
        return cardRepository.findById(id);
    }

    public Card getByAccountId(Long accountId) {
        return cardRepository.findByAccountId(accountId);
    }

    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    public void delete(Long id) {
        Card card = cardRepository.findById(id);

        if (card == null) {
            throw new IllegalArgumentException("Card not found");
        }

        cardRepository.delete(id);
    }
}