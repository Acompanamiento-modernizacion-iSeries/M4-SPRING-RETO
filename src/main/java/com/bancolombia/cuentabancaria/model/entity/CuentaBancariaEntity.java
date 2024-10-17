package com.bancolombia.cuentabancaria.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipocuenta", discriminatorType = DiscriminatorType.STRING)
@Table(name = "cuentabancaria")
public abstract class CuentaBancariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected  Long id;

    protected  String cuenta;

    protected  BigDecimal saldo;

    @OneToMany(mappedBy = "cuentabancaria")
    protected  List<TransaccionEntity> transacciones;

    public CuentaBancariaEntity() {
    }

    public CuentaBancariaEntity(Long id, String cuenta, BigDecimal saldo, List<TransaccionEntity> transacciones) {
        this.id = id;
        this.cuenta = cuenta;
        this.saldo = saldo;
        this.transacciones = transacciones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<TransaccionEntity> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<TransaccionEntity> transacciones) {
        this.transacciones = transacciones;
    }

    public abstract void depositoSucursal(BigDecimal valor);
    public abstract void depositoCajero(BigDecimal valor);
    public abstract void depositoCuenta(BigDecimal valor);
    public abstract void compraFisico(BigDecimal valor);
    public abstract void compraWeb(BigDecimal valor);
    public abstract void retiroCajero(BigDecimal valor);
}
