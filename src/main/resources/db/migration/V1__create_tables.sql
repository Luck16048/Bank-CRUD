CREATE TABLE user_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    passport_number VARCHAR(50) NOT NULL UNIQUE,
    joining_date DATE NOT NULL,
    photo VARCHAR(500)
);


CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    iban VARCHAR(28) NOT NULL UNIQUE,
    account_type VARCHAR(20) NOT NULL,
    deposit_daily_limit DECIMAL(15, 2) NOT NULL,
    withdrawal_daily_limit DECIMAL(15, 2) NOT NULL,
    user_profile_id BIGINT NOT NULL,

    CONSTRAINT fk_account_user FOREIGN KEY (user_profile_id) REFERENCES user_profile(id) ON DELETE CASCADE,
    CONSTRAINT chk_account_type CHECK (account_type IN ('Chequing', 'Saving', 'Investment')),
    CONSTRAINT chk_deposit_limit CHECK (deposit_daily_limit >= 0),
    CONSTRAINT chk_withdrawal_limit CHECK (withdrawal_daily_limit >= 0),
    CONSTRAINT chk_iban CHECK (iban REGEXP '^[A-Za-z]{2}[0-9]{26}$')
);


CREATE TABLE card (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(16) NOT NULL UNIQUE,
    account_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_card_account FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    CONSTRAINT chk_card_number CHECK (number REGEXP '^[0-9]{16}$')
);