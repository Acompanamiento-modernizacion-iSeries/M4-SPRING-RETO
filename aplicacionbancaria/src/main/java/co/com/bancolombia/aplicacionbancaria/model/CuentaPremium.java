package co.com.bancolombia.aplicacionbancaria.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("PREMIUM")
public class CuentaPremium extends Cuenta {

    public CuentaPremium() { }

    public CuentaPremium(Long id, String numero, String titular, BigDecimal saldo) {
        super(id, numero, titular, saldo);
    }

    @Override
    public String tipoCuenta() {
        return "Cuenta Premium";
    }

    public void depositoSucursal(BigDecimal monto) {
        if (!deposito(monto))
            throw new IllegalArgumentException("El monto ingresado es menor que Cero!");
    }

    public void depositoCajeroAuto(BigDecimal monto) {
        if (!deposito(monto))
            throw new IllegalArgumentException("El monto ingresado es menor que Cero!");
    }

    public void depositoOtraCta(BigDecimal monto) {
        BigDecimal comisionDepositoOtraCta = new BigDecimal("1.5");
        if (!deposito(monto.subtract(comisionDepositoOtraCta)))
            throw new IllegalArgumentException("El monto ingresado es menor que Cero!");
    }

    public void compraFisica(BigDecimal monto) {
        if (!retiro(monto))
            throw new IllegalArgumentException("No tiene fondos suficientes!");
    }

    public void compraVirtual(BigDecimal monto) {
        BigDecimal seguroCompraVirtual = new BigDecimal("5.0");
        if (!retiro(monto.add(seguroCompraVirtual)))
            throw new IllegalArgumentException("No tiene fondos suficientes para cubrir el monto y el seguro de compra!");
    }

    public void retiroCajero(BigDecimal monto) {
        if (!retiro(monto))
            throw new IllegalArgumentException("No tiene fondos suficientes!");
    }

    public void retiroOtraCta(BigDecimal monto) {
        if (!retiro(monto))
            throw new IllegalArgumentException("No tiene fondos suficientes!");
    }
}
