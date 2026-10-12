package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.UserProfileRepository;

import java.util.List;

public class AccountService {

    private final AccountRepository accountRepository;
    private final UserProfileRepository userProfileRepository;

    public AccountService(
            AccountRepository accountRepository,
            UserProfileRepository userProfileRepository
    ) {
        this.accountRepository = accountRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public Long create(Account account) {
        if (account.getIbanNumber() == null ||
                !account.getIbanNumber().matches("[A-Z]{2}[0-9]{26}")) {

            throw new IllegalArgumentException(
                    "IBAN must contain 2 letters followed by 26 digits"
            );
        }

        if (account.getAccountType() == null) {
            throw new IllegalArgumentException("Account type is required");
        }

        if (account.getDepositDailyLimit() == null ||
                account.getDepositDailyLimit().signum() < 0) {

            throw new IllegalArgumentException(
                    "Deposit daily limit cannot be negative"
            );
        }

        if (account.getWithdrawalDailyLimit() == null ||
                account.getWithdrawalDailyLimit().signum() < 0) {

            throw new IllegalArgumentException(
                    "Withdrawal daily limit cannot be negative"
            );
        }

        if (account.getUserProfileId() == null) {
            throw new IllegalArgumentException(
                    "User profile id is required"
            );
        }

        if (userProfileRepository.findById(account.getUserProfileId()) == null) {
            throw new IllegalArgumentException(
                    "User profile not found"
            );
        }

        return accountRepository.create(account);
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getById(Long id) {
        return accountRepository.findById(id);
    }

    public List<Account> getByUserProfileId(Long userProfileId) {
        return accountRepository.findByUserProfileId(userProfileId);
    }

    public void delete(Long id) {
        Account account = accountRepository.findById(id);

        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        accountRepository.delete(id);
    }
}