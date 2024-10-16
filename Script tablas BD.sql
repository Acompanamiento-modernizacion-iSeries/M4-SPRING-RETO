--Tablas Reto Final Spring - APP bancaria
--Juan Pablo Valderrama.

--Tabla para manejo de cuentas.
CREATE TABLE cuenta_bancaria (
	id_cuenta SERIAL PRIMARY KEY,
    nro_cuenta BIGINT UNIQUE NOT NULL,
	tipo_cuenta VARCHAR(50),
    saldo NUMERIC(15, 2),
    titular VARCHAR(255),
    documento_titular VARCHAR(50),
    telefono VARCHAR(20),
    direccion VARCHAR(255),
	email VARCHAR(80)
);

--Tabla para historia de transacciones de cada cuenta.
CREATE TABLE transaccion (
    id_transaccion SERIAL PRIMARY KEY,
    cuenta_asociada BIGINT REFERENCES cuenta_bancaria(id_cuenta),
    tipo_transaccion VARCHAR(100),
    valor NUMERIC(15, 2),
	comision NUMERIC(15, 2),
    fecha DATE,
    hora TIME
);

--Consultas
SELECT * FROM cuenta_bancaria;
SELECT * FROM transaccion;
SELECT * FROM transaccion where cuenta_asociada = 2 ORDER BY fecha DESC, hora DESC;


