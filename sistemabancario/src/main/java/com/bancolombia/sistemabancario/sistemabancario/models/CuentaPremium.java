package com.bancolombia.sistemabancario.sistemabancario.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue("2")
public class CuentaPremium extends Cuenta{

    @Override
    public Cuenta DepositoDesdecajeroAutomatico(BigDecimal valorDeposito) {
        this.setSaldo(getSaldo().add(valorDeposito));
        return this;
    }

    @Override
    void RetiroEnCajero(BigDecimal valorRetiro) {
        if(this.saldo.compareTo(valorRetiro) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubiri el valor del retiro");
        }
        this.setSaldo(getSaldo().subtract(valorRetiro));

    }
}
