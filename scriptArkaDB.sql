-- Tabla provider
CREATE TABLE provider (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telephone VARCHAR(50),
    country VARCHAR(50),
    category VARCHAR(50)
);

-- Tabla store
CREATE TABLE store (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    telephone VARCHAR(50),
    country VARCHAR(50),
    city VARCHAR(50),
    address VARCHAR(200)
);

-- Tabla stock_store
CREATE TABLE stock_store (
    id BIGSERIAL PRIMARY KEY,
    store_id BIGINT NOT NULL,
    product_id VARCHAR(50) NOT NULL,
    provider_id BIGINT NOT NULL,
    amount INT NOT NULL DEFAULT 0,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (store_id, product_id, provider_id),
    FOREIGN KEY (store_id) REFERENCES store(id),
    FOREIGN KEY (provider_id) REFERENCES provider(id)
);

-- Tabla users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Tabla tokens
CREATE TABLE tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Table: inventory_movements
CREATE TABLE inventory_movements (
    id SERIAL PRIMARY KEY,
    operation_date TIMESTAMP NOT NULL,
    operation_type VARCHAR(50) NOT NULL,
    product_id VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    previous_quantity INT NOT NULL,
    final_quantity INT NOT NULL
);

-- Table: sales_header
CREATE TABLE sales_headers (
    id INT PRIMARY KEY,
    sale_date DATE,
    customer_id VARCHAR(50),
    customer_name VARCHAR(50),
    customer_email VARCHAR(50),
    sale_value NUMERIC(18,0)
);

-- Table: orders
CREATE TABLE orders (
    id INT PRIMARY KEY,
    sales_header_id INT,
    order_description VARCHAR(50),
    delivery_address VARCHAR(50),
    status INT,
    CONSTRAINT fk_orders_sales_header FOREIGN KEY (sales_header_id)
        REFERENCES sales_headers (id)
);

-- Table: sales_details
CREATE TABLE sales_details (
    id INT PRIMARY KEY,
    sale_date DATE NOT NULL,
    sales_header_id INT NOT NULL,
    product_id VARCHAR(50) NOT NULL,
    price NUMERIC(18,0) NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT fk_sales_details_sales_header FOREIGN KEY (sales_header_id)
        REFERENCES sales_headers (id)
);

-- Trigger para actualizar automáticamente la fecha
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_date = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_stock_store
BEFORE UPDATE ON stock_store
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();


-- ============================================
-- FUNCIONES (equivalentes a los procedimientos MySQL)
-- ============================================

-- 1️⃣ Obtener stock
CREATE OR REPLACE FUNCTION get_stock(
    p_store_id BIGINT,
    p_product_id VARCHAR,
    p_provider_id BIGINT
)
RETURNS TABLE (
    id BIGINT,
    store_id BIGINT,
    product_id VARCHAR,
    provider_id BIGINT,
    amount INT,
    update_date TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT ss.id, ss.store_id, ss.product_id, ss.provider_id, ss.amount, ss.update_date
    FROM stock_store ss
    WHERE
        (p_store_id = 0 OR ss.store_id = p_store_id)
        AND (p_product_id = '' OR ss.product_id = p_product_id)
        AND (p_provider_id = 0 OR ss.provider_id = p_provider_id);
END;
$$ LANGUAGE plpgsql;

-- Ejemplo:
-- SELECT * FROM get_stock(0, '', 0);
-- SELECT * FROM get_stock(1, '', 0);
-- SELECT * FROM get_stock(1, '68f32fb04d1d169a90c5fced', 2);

-- 2️⃣ Upsert (insertar o actualizar)
CREATE OR REPLACE FUNCTION upsert_stock(
    p_store_id BIGINT,
    p_product_id VARCHAR,
    p_provider_id BIGINT,
    p_amount INT
)
RETURNS VOID AS $$
BEGIN
    INSERT INTO stock_store (store_id, product_id, provider_id, amount)
    VALUES (p_store_id, p_product_id, p_provider_id, p_amount)
    ON CONFLICT (store_id, product_id, provider_id)
    DO UPDATE SET
        amount = EXCLUDED.amount,
        update_date = CURRENT_TIMESTAMP;
END;
$$ LANGUAGE plpgsql;

-- Ejemplo:
-- SELECT upsert_stock(1, '68f32fb04d1d169a90c5fced', 2, 50);

-- 3️⃣ Actualizar cantidad (incremento o decremento)
CREATE OR REPLACE FUNCTION update_stock_amount(
    p_store_id BIGINT,
    p_product_id VARCHAR,
    p_provider_id BIGINT,
    p_delta INT
)
RETURNS VOID AS $$
BEGIN
    UPDATE stock_store
    SET amount = amount + p_delta,
        update_date = CURRENT_TIMESTAMP
    WHERE store_id = p_store_id
      AND product_id = p_product_id
      AND provider_id = p_provider_id;
END;
$$ LANGUAGE plpgsql;

-- Ejemplo:
-- SELECT update_stock_amount(1, '68f32fb04d1d169a90c5fced', 2, 5);