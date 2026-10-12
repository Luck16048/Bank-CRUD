package com.example.entity;

public class Card {

    private Long id;
    private String number;
    private Long accountId;

    public Card() {
    }

    public Card(
            Long id,
            String number,
            Long accountId
    ) {
        this.id = id;
        this.number = number;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}