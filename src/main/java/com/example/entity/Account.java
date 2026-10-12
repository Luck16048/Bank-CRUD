package com.example.entity;

import java.math.BigDecimal;

public class Account {

    private Long id;
    private String ibanNumber;
    private AccountTypeEnum accountType;
    private BigDecimal depositDailyLimit;
    private BigDecimal withdrawalDailyLimit;
    private Long userProfileId;

    public Account(
            Long id,
            String ibanNumber,
            AccountTypeEnum accountType,
            BigDecimal depositDailyLimit,
            BigDecimal withdrawalDailyLimit,
            Long userProfileId
    ) {
        this.id = id;
        this.ibanNumber = ibanNumber;
        this.accountType = accountType;
        this.depositDailyLimit = depositDailyLimit;
        this.withdrawalDailyLimit = withdrawalDailyLimit;
        this.userProfileId = userProfileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIbanNumber() {
        return ibanNumber;
    }

    public void setIbanNumber(String ibanNumber) {
        this.ibanNumber = ibanNumber;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getDepositDailyLimit() {
        return depositDailyLimit;
    }

    public void setDepositDailyLimit(BigDecimal depositDailyLimit) {
        this.depositDailyLimit = depositDailyLimit;
    }

    public BigDecimal getWithdrawalDailyLimit() {
        return withdrawalDailyLimit;
    }

    public void setWithdrawalDailyLimit(BigDecimal withdrawalDailyLimit) {
        this.withdrawalDailyLimit = withdrawalDailyLimit;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }
}