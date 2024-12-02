-- Inserindo produtos iniciais
INSERT INTO products (name, description, price, quantity, minimum_stock, created_at, updated_at)
VALUES 
('Pão Francês', 'Pão tradicional francês', 0.50, 100, 20, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Pão de Queijo', 'Pão de queijo mineiro', 3.00, 50, 10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Croissant', 'Croissant tradicional', 4.50, 30, 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Bolo de Chocolate', 'Bolo caseiro de chocolate', 25.00, 10, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Sonho', 'Sonho recheado com doce de leite', 3.50, 40, 8, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Coxinha', 'Coxinha de frango', 5.00, 30, 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Pão Integral', 'Pão integral de grãos', 6.00, 25, 5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Brigadeiro', 'Brigadeiro gourmet', 2.50, 60, 15, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Quiche', 'Quiche de queijo', 8.00, 15, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Torta de Frango', 'Torta de frango com catupiry', 30.00, 8, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
