CREATE TABLE Cuenta (
    cuenta_id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    titular VARCHAR(20) NOT NULL,
    saldo DECIMAL(15, 2),
	tipo_cuenta VARCHAR(20) NOT NULL CHECK (tipo_transacc IN ('BASICA', 'PREMIUM'),
);

INSERT INTO Cuenta (numero, titular, saldo, tipo_cuenta) 
VALUES 
('1234567890', 'Ricardo', 1000.50, 'BASICA'),
('1122334455', 'Jorge', 500.75, 'PREMIUM'),
('1112223334', 'Santiago', 1500.00, 'BASICA');

select * from Cuenta;
 
CREATE TABLE Transaccion (
    id SERIAL PRIMARY KEY,
    cuenta_id INTEGER REFERENCES Cuenta(id) NOT NULL,
    tipo_transacc VARCHAR(20) NOT NULL,
    monto DECIMAL(15, 2) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
	fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

select * from Transaccion;