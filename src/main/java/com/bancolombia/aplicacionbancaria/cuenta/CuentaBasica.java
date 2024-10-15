package com.bancolombia.aplicacionbancaria.cuenta;

import com.bancolombia.aplicacionbancaria.config.Exceptions;
import com.bancolombia.aplicacionbancaria.transaccion.Transaccion;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Básica")
@NoArgsConstructor
public class CuentaBasica extends Cuenta {

    public CuentaBasica(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    public CuentaBasica() {
        super();
    }

    @Override
    public double depositar(double monto, String tipoOperacion) {
        double costo = 0;

        switch (tipoOperacion) {
            case "sucursal":
                costo = 0;
                break;
            case "cajero":
                costo = 2;
                break;
            case "otraCuenta":
                costo = 1.5;
                break;
            default:
                throw new IllegalArgumentException("Tipo de operación no válido");
        }
        setSaldo(getSaldo() + monto - costo);
        Transaccion transaccion = new Transaccion("Depósito", monto, LocalDateTime.now(), this);
        agregarTransaccion(transaccion);
        return getSaldo();
    }

    @Override
    public double retirar(double monto) {
        if (monto + 1 > getSaldo()) {
            throw new Exceptions.SaldoInsuficienteException("Saldo insuficiente");
        }
        setSaldo(getSaldo() - (monto + 1));
        Transaccion transaccion = new Transaccion("Retiro", monto, LocalDateTime.now(), this);
        agregarTransaccion(transaccion);
        return getSaldo();
    }

    @Override
    public double comprar(double monto, boolean esCompraWeb) {
        if (esCompraWeb) {
            setSaldo(getSaldo() - (monto + 5));
        } else {
            setSaldo(getSaldo() - monto);
        }
        Transaccion transaccion = new Transaccion("Compra", monto, LocalDateTime.now(), this);
        agregarTransaccion(transaccion);
        return getSaldo();
    }
}

