package com.bancolombia.aplicacionbancaria.cuenta;

import com.bancolombia.aplicacionbancaria.config.Exceptions;
import com.bancolombia.aplicacionbancaria.transaccion.Transaccion;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Premium")
public class CuentaPremium extends Cuenta {

    public CuentaPremium(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
    }

    public CuentaPremium() {
        super();
    }

    @Override
    public double depositar(double monto, String tipoOperacion) {
        setSaldo(getSaldo() + monto);
        Transaccion transaccion = new Transaccion("Depósito", monto, LocalDateTime.now(), this);
        agregarTransaccion(transaccion);
        return getSaldo();
    }

    @Override
    public double retirar(double monto) {
        if (monto > getSaldo()) {
            throw new Exceptions.SaldoInsuficienteException("Saldo insuficiente");
        }
        setSaldo(getSaldo() - monto);
        Transaccion transaccion = new Transaccion("Retiro", monto, LocalDateTime.now(), this);
        agregarTransaccion(transaccion);
        return getSaldo();
    }

    @Override
    public double comprar(double monto, boolean esCompraWeb) {
        if (esCompraWeb) {
            setSaldo(getSaldo() - (monto));
        } else {
            setSaldo(getSaldo() - monto);
        }
        Transaccion transaccion = new Transaccion("Compra", monto, LocalDateTime.now(), this);
        agregarTransaccion(transaccion);
        return getSaldo();
    }
}
