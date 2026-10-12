package com.example.repository;

import org.jooq.Field;
import org.jooq.Table;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;

public class DatabaseTables {

    public static final Table<?> USER_PROFILE =
            table(name("user_profile"));

    public static final Field<Long> USER_ID =
            field(name("id"), Long.class);

    public static final Field<String> USER_FIRST_NAME =
            field(name("first_name"), String.class);

    public static final Field<String> USER_LAST_NAME =
            field(name("last_name"), String.class);

    public static final Field<String> USER_PASSPORT =
            field(name("passport_number"), String.class);

    public static final Field<java.time.LocalDate> USER_JOINING_DATE =
            field(name("joining_date"), java.time.LocalDate.class);

    public static final Field<String> USER_PHOTO =
            field(name("photo"), String.class);


    public static final Table<?> ACCOUNT =
            table(name("account"));

    public static final Field<Long> ACCOUNT_ID =
            field(name("id"), Long.class);

    public static final Field<String> ACCOUNT_IBAN =
            field(name("iban"), String.class);

    public static final Field<String> ACCOUNT_TYPE =
            field(name("account_type"), String.class);

    public static final Field<java.math.BigDecimal> ACCOUNT_DEPOSIT_LIMIT =
            field(name("deposit_daily_limit"), java.math.BigDecimal.class);

    public static final Field<java.math.BigDecimal> ACCOUNT_WITHDRAWAL_LIMIT =
            field(name("withdrawal_daily_limit"), java.math.BigDecimal.class);

    public static final Field<Long> ACCOUNT_USER_ID =
            field(name("user_profile_id"), Long.class);


    public static final Table<?> CARD =
            table(name("card"));

    public static final Field<Long> CARD_ID =
            field(name("id"), Long.class);

    public static final Field<String> CARD_NUMBER =
            field(name("number"), String.class);

    public static final Field<Long> CARD_ACCOUNT_ID =
            field(name("account_id"), Long.class);
}