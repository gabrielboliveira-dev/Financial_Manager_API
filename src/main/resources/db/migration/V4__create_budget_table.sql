CREATE TABLE IF NOT EXISTS budgets (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    user_id UUID NOT NULL,
    category_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
