package com.bancolombia.sistemabancario.sistemabancario.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cuenta", discriminatorType = DiscriminatorType.INTEGER)
@Table(name="cuenta_bancaria")
public abstract class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    protected Long CuentaId;

    @Column(name = "numero_cuenta")
    protected Integer numeroCuenta;


    protected BigDecimal saldo;
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    protected Set<Transaccion> historicoTransacciones;

    public Cuenta() {
        historicoTransacciones = new HashSet<>();;
    }

    public Cuenta(Long cuentaId, Integer numeroCuenta, BigDecimal saldo) {
        CuentaId = cuentaId;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    public Long getCuentaId() {
        return CuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        CuentaId = cuentaId;
    }

    public Integer getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Integer numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }


    public Set<Transaccion> getHistoricoTransacciones() {
        return historicoTransacciones;
    }

    public void setHistoricoTransacciones(Set<Transaccion> historicoTransacciones) {
        this.historicoTransacciones = historicoTransacciones;
    }

    public Cuenta DepositoDesdeSucursal(BigDecimal valorDeposito){
        this.setSaldo(getSaldo().add(valorDeposito));
        return this;
    }

    abstract public Cuenta DepositoDesdecajeroAutomatico(BigDecimal valorDeposito);

    public Cuenta DepositoDesdeOtrasCuentas(BigDecimal valorDeposito){
        BigDecimal costoDeposito = new BigDecimal(1.5 * 4610.09);
        if(this.saldo.compareTo(valorDeposito) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir el costo del depósito");
        }
        this.setSaldo(getSaldo().subtract(costoDeposito));
        this.setSaldo(getSaldo().add(valorDeposito));
        return this;
    }

    public Cuenta CompraEnEstablecimientoFisico(BigDecimal ValorCompra){
        if(this.saldo.compareTo(ValorCompra) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir la compra");
        }
        this.setSaldo(getSaldo().subtract(ValorCompra));
        return this;
    }

    public Cuenta CompraEnpaginaWeb(BigDecimal valorCompra){
        BigDecimal valorSeguro = new BigDecimal(5 * 4610.09);
        BigDecimal ValorCompraMasSeguro = new BigDecimal(0);
        ValorCompraMasSeguro = ValorCompraMasSeguro.add(valorCompra).add(valorSeguro);
        if(this.saldo.compareTo(ValorCompraMasSeguro) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir el valor de compra más el seguro");
        }
        this.setSaldo(getSaldo().subtract(ValorCompraMasSeguro));
        return this;
    }

    abstract void RetiroEnCajero(BigDecimal valorRetiro);

    @Override
    public String toString() {
        return " Datos de la cuenta cuenta\n" +
                "Id de la cuenta :" + CuentaId +
                "\n" +
                "numero de la cuenta: " + numeroCuenta +
                "saldo : " + saldo ;
    }
}
