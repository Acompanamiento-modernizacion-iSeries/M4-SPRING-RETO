package co.bancolombia.aplicacionbancaria.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("1")
public class CuentaBasica extends Cuenta{
    public static final BigDecimal COSTO_RETIRO = BigDecimal.valueOf(1.0);
    public static final BigDecimal COSTO_DEPOSITO = BigDecimal.valueOf(2.0);
    @Override
    public Cuenta DepositoCajero(BigDecimal valorDeposito) {
        BigDecimal valorDepositoMasCosto = new BigDecimal(String.valueOf(valorDeposito.add(COSTO_DEPOSITO)));
        if(this.saldo.compareTo(valorDepositoMasCosto) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir el costo del depósito 1");
        }
        this.setSaldo(getSaldo().subtract(COSTO_DEPOSITO));
        this.setSaldo(getSaldo().add(valorDeposito));
        return this;
    }

    @Override
    public Cuenta RetiroCajero(BigDecimal valorRetiro) {
        BigDecimal valorRetirMasCosto = new BigDecimal(String.valueOf(valorRetiro.add(COSTO_RETIRO)));
        if(this.saldo.compareTo(valorRetirMasCosto) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir el valor del retiro más el costo");
        }
        this.setSaldo(getSaldo().subtract(valorRetirMasCosto));
        return this;
    }

}