package com.bancolombia.sistemabancario.sistemabancario.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue("1")
public class CuentaBasica extends Cuenta{


    @Override
    public Cuenta DepositoDesdecajeroAutomatico(BigDecimal valorDeposito) {
        BigDecimal costoDeposito = new BigDecimal(2 * 4610.09);
        if(this.saldo.compareTo(costoDeposito) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir el costo del depósito");
        }
        this.setSaldo(getSaldo().subtract(costoDeposito));
        this.setSaldo(getSaldo().add(valorDeposito));
        return this;
    }

    @Override
    void RetiroEnCajero(BigDecimal valorRetiro) {
        BigDecimal costoRetiro = new BigDecimal(1 * 4610.09);
        BigDecimal valorRetirMasCosto = new BigDecimal(0);
        if(this.saldo.compareTo(valorRetirMasCosto) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir el valor del retiro más el costo");
        }
        this.setSaldo(getSaldo().subtract(valorRetirMasCosto));

    }

}
