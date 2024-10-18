package co.bancolombia.aplicacionbancaria.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class CuentaPremium extends Cuenta{

    public CuentaPremium(Long nroCuenta, BigDecimal saldo, String nombreCliente) {
        super(nroCuenta, saldo, nombreCliente);
    }

    public CuentaPremium() {
        super();
    }

    @Override
    public void depositoSucursal(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().add(monto));
    }

    @Override
    public void depositoCajero(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().add(monto));
    }

    @Override
    public void depositoOtraCuenta(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().add(monto.subtract(BigDecimal.valueOf(1500))));
    }

    @Override
    public void compraFisica(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().subtract(monto));
    }

    @Override
    public void compraWeb(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().subtract(monto.add(BigDecimal.valueOf(5))));
    }

    @Override
    public void retiroCajero(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().subtract(monto));
    }
}
