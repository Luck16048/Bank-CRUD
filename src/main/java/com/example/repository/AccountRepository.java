package com.example.repository;

import com.example.entity.Account;
import com.example.entity.AccountTypeEnum;
import org.jooq.DSLContext;

import java.util.List;

import static com.example.repository.DatabaseTables.*;

public class AccountRepository {

    private final DSLContext dsl;

    public AccountRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Long create(Account account) {
        dsl.insertInto(ACCOUNT)
                .set(ACCOUNT_IBAN, account.getIbanNumber())
                .set(ACCOUNT_TYPE, account.getAccountType().name())
                .set(ACCOUNT_DEPOSIT_LIMIT, account.getDepositDailyLimit())
                .set(ACCOUNT_WITHDRAWAL_LIMIT, account.getWithdrawalDailyLimit())
                .set(ACCOUNT_USER_ID, account.getUserProfileId())
                .execute();

        return dsl.select(ACCOUNT_ID)
                .from(ACCOUNT)
                .where(ACCOUNT_IBAN.eq(account.getIbanNumber()))
                .fetchOne(ACCOUNT_ID);
    }

    public List<Account> findAll() {
        return dsl.select()
                .from(ACCOUNT)
                .fetch(record -> map(record));
    }

    public Account findById(Long id) {
        var record = dsl.select()
                .from(ACCOUNT)
                .where(ACCOUNT_ID.eq(id))
                .fetchOne();

        return record == null ? null : map(record);
    }

    public List<Account> findByUserProfileId(Long userProfileId) {
        return dsl.select()
                .from(ACCOUNT)
                .where(ACCOUNT_USER_ID.eq(userProfileId))
                .fetch(record -> map(record));
    }

    public int delete(Long id) {
        return dsl.deleteFrom(ACCOUNT)
                .where(ACCOUNT_ID.eq(id))
                .execute();
    }

    private Account map(org.jooq.Record record) {
        return new Account(
                record.get(ACCOUNT_ID),
                record.get(ACCOUNT_IBAN),
                AccountTypeEnum.valueOf(record.get(ACCOUNT_TYPE)),
                record.get(ACCOUNT_DEPOSIT_LIMIT),
                record.get(ACCOUNT_WITHDRAWAL_LIMIT),
                record.get(ACCOUNT_USER_ID)
        );
    }
}