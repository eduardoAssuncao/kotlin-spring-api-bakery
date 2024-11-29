-- Insert sample bakery products
INSERT INTO products (name, description, price, quantity, minimum_stock, created_at, updated_at) 
VALUES 
    ('Pão Francês', 'Pão tradicional francês', 0.50, 100, 20, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Croissant', 'Croissant folhado tradicional', 4.50, 30, 10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Bolo de Chocolate', 'Bolo de chocolate com cobertura', 35.00, 5, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Pão de Queijo', 'Pão de queijo mineiro', 2.50, 50, 15, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Rosquinha', 'Rosquinha coberta com açúcar', 1.50, 40, 12, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('Sonho', 'Sonho recheado com doce de leite', 3.50, 25, 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
