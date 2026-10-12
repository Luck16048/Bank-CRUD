package com.example.repository;

import com.example.entity.Card;
import org.jooq.DSLContext;

import java.util.List;

import static com.example.repository.DatabaseTables.*;

public class CardRepository {

    private final DSLContext dsl;

    public CardRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Long create(Card card) {
        dsl.insertInto(CARD)
                .set(CARD_NUMBER, card.getNumber())
                .set(CARD_ACCOUNT_ID, card.getAccountId())
                .execute();

        return dsl.select(CARD_ID)
                .from(CARD)
                .where(CARD_NUMBER.eq(card.getNumber()))
                .fetchOne(CARD_ID);
    }

    public Card findById(Long id) {
        var record = dsl.select()
                .from(CARD)
                .where(CARD_ID.eq(id))
                .fetchOne();

        if (record == null) {
            return null;
        }
        return map(record);
    }

    public Card findByAccountId(Long accountId) {
        var record = dsl.select()
                .from(CARD)
                .where(CARD_ACCOUNT_ID.eq(accountId))
                .fetchOne();

        if (record == null) {
            return null;
        }
        return map(record);
    }

    public List<Card> findAll() {
        return dsl.select()
                .from(CARD)
                .fetch(record -> map(record));
    }

    public int delete(Long id) {
        return dsl.deleteFrom(CARD)
                .where(CARD_ID.eq(id))
                .execute();
    }

    private Card map(org.jooq.Record record) {
        return new Card(
                record.get(CARD_ID),
                record.get(CARD_NUMBER),
                record.get(CARD_ACCOUNT_ID)
        );
    }
}