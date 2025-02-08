-- Table for Car Owner
CREATE TABLE car_owner (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15) NOT NULL
);

-- Table for End Customer
CREATE TABLE end_customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    address TEXT
);

-- Table for Admin
CREATE TABLE admin (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Table for Car
CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    owner_id INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Available',
    FOREIGN KEY (owner_id) REFERENCES car_owner (id)
);

-- Table for Lease
CREATE TABLE lease (
    id SERIAL PRIMARY KEY,
    car_id INT NOT NULL,
    customer_id INT NOT NULL,
    lease_start_date DATE NOT NULL,
    lease_end_date DATE,
    lease_status VARCHAR(20) NOT NULL DEFAULT 'Active',
    FOREIGN KEY (car_id) REFERENCES car (id),
    FOREIGN KEY (customer_id) REFERENCES end_customer (id)
);

-- Table for Car Lease History
CREATE TABLE lease_history (
    id SERIAL PRIMARY KEY,
    lease_id INT NOT NULL,
    change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    previous_status VARCHAR(20),
    new_status VARCHAR(20),
    FOREIGN KEY (lease_id) REFERENCES lease (id)
);


