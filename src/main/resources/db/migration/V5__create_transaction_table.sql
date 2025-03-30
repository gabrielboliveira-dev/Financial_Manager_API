CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    transaction_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    type VARCHAR(50) NOT NULL, -- Ex: 'INCOME', 'EXPENSE'
    account_id UUID NOT NULL,
    category_id UUID NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(id),
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);