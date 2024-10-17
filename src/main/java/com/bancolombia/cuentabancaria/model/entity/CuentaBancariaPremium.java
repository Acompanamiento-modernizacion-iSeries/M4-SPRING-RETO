package com.bancolombia.cuentabancaria.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue("PREMIUM")
public class CuentaBancariaPremium extends CuentaBancariaEntity{

    public CuentaBancariaPremium() {
        super();
    }

    public CuentaBancariaPremium(Long id, String cuenta, BigDecimal saldo, List<TransaccionEntity> transacciones) {
        super(id, cuenta, saldo, transacciones);
    }

    @Override
    public void depositoSucursal(BigDecimal valor){
        saldo = saldo.add(valor);
    }

    @Override
    public void depositoCajero(BigDecimal valor){
        saldo = saldo.add(valor);
    }

    @Override
    public void depositoCuenta(BigDecimal valor){
        saldo = saldo.add(valor).subtract(new BigDecimal(1.5));
    }

    @Override
    public void compraFisico(BigDecimal valor){
        saldo = saldo.subtract(valor);
    }

    @Override
    public void compraWeb(BigDecimal valor){
        saldo = saldo.subtract(valor).subtract(new BigDecimal(5));
    }

    @Override
    public void retiroCajero(BigDecimal valor){
        saldo = saldo.subtract(valor);
    }
}
