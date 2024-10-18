package co.bancolombia.aplicacionbancaria.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class CuentaBasica extends Cuenta{

    public CuentaBasica(Long nroCuenta, BigDecimal saldo, String nombreCliente) {
        super(nroCuenta, saldo, nombreCliente);
    }

    public CuentaBasica() {
        super();
    }

    @Override
    public void depositoSucursal(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().add(monto));
    }

    @Override
    public void depositoCajero(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().add(monto.subtract(BigDecimal.valueOf(2))));
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
        this.asignarSaldo(this.consultarSaldo().subtract(monto.add(BigDecimal.valueOf(1))));
    }
}
