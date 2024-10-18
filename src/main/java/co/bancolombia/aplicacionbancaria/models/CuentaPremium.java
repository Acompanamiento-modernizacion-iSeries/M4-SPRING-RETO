package co.bancolombia.aplicacionbancaria.models;

import co.bancolombia.aplicacionbancaria.models.Cuenta;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("2")
public class CuentaPremium extends Cuenta {

    @Override
    public Cuenta DepositoCajero(BigDecimal valorDeposito) {
        this.setSaldo(getSaldo().add(valorDeposito));
        return this;
    }

    @Override
    public Cuenta RetiroCajero(BigDecimal valorRetiro) {
    //void RetiroEnCajero(BigDecimal valorRetiro) {
        if(this.saldo.compareTo(valorRetiro) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubiri el valor del retiro");
        }
        this.setSaldo(getSaldo().subtract(valorRetiro));
        return this;
    }
}
