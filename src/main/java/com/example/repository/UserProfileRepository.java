package com.example.repository;

import com.example.entity.UserProfile;
import org.jooq.DSLContext;

import java.time.LocalDate;
import java.util.List;
import static com.example.repository.DatabaseTables.*;


public class UserProfileRepository {

    private final DSLContext dsl;

    public UserProfileRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Long create(UserProfile userProfile) {
        dsl.insertInto(USER_PROFILE)
                .set(USER_FIRST_NAME, userProfile.getFirstName())
                .set(USER_LAST_NAME, userProfile.getLastName())
                .set(USER_PASSPORT, userProfile.getPassportNumber())
                .set(USER_JOINING_DATE, userProfile.getJoiningDate())
                .set(USER_PHOTO, userProfile.getPhoto())
                .execute();

        return dsl.select(USER_ID)
                .from(USER_PROFILE)
                .where(USER_PASSPORT.eq(userProfile.getPassportNumber()))
                .fetchOne(USER_ID);
    }

    public List<UserProfile> findAll() {
        return dsl.select()
                .from(USER_PROFILE)
                .fetch(record -> new UserProfile(
                        record.get(USER_ID),
                        record.get(USER_FIRST_NAME),
                        record.get(USER_LAST_NAME),
                        record.get(USER_PASSPORT),
                        record.get(USER_JOINING_DATE, LocalDate.class),
                        record.get(USER_PHOTO)
                ));
    }

    public UserProfile findById(Long id) {
        var record = dsl.select()
                .from(USER_PROFILE)
                .where(USER_ID.eq(id))
                .fetchOne();

        if (record == null) {
            return null;
        }

        return new UserProfile(
                record.get(USER_ID),
                record.get(USER_FIRST_NAME),
                record.get(USER_LAST_NAME),
                record.get(USER_PASSPORT),
                record.get(USER_JOINING_DATE, LocalDate.class),
                record.get(USER_PHOTO)
        );
    }

    public int delete(Long id) {
        return dsl.deleteFrom(USER_PROFILE)
                .where(USER_ID.eq(id))
                .execute();
    }
}