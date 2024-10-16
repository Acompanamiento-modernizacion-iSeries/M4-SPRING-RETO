package co.bancolombia.aplicacionbancaria.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class CuentaPremium extends Cuenta {

    public CuentaPremium(Long nroCuenta, BigDecimal saldo, String titular, String documentoTitular, String telefono, String direccion, String email) {
        super(nroCuenta, saldo, titular, documentoTitular, telefono, direccion, email);
    }

    public CuentaPremium() {
        super();
    }

    //Método para realizar un depósito en la cuenta desde una sucursal, no cobra comisión.
    @Override
    public void depositoSucursal(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().add(monto));
    }

    //Método para realizar un depósito en la cuenta desde un cajero, no cobra comisión.
    @Override
    public void depositoDesdeCajero(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().add(monto));
    }

    //Método para realizar un depósito en la cuenta desde otra cuenta, cobra comisión de $1500.
    @Override
    public void depositoDesdeOtraCuenta(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().add(monto.subtract(BigDecimal.valueOf(1500))));
    }

    //Método para realizar una compra física, no cobra comisión.
    @Override
    public void compraFisica(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().subtract(monto));
    }

    //Método para realizar una compra web, cobra comisión de $5000.
    @Override
    public void compraWeb(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().subtract(monto.add(BigDecimal.valueOf(5000))));
    }

    //Método para realizar un retiro en el cajero, no cobra comisión.
    @Override
    public void retiroCajero(BigDecimal monto) {
        this.asignarSaldo(this.consultarSaldo().subtract(monto));
    }

}