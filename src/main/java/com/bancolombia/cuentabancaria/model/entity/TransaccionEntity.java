package com.bancolombia.cuentabancaria.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaccion")
public class TransaccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipotransaccion;

    private BigDecimal monto;

    private Date fecharegistro;

    @ManyToOne
    @JoinColumn(name = "idcuenta")
    private CuentaBancariaEntity cuentabancaria;

    public TransaccionEntity() {
    }

    public TransaccionEntity(Long id, String tipotransaccion, BigDecimal monto, Date fecharegistro, CuentaBancariaEntity cuentabancaria) {
        this.id = id;
        this.tipotransaccion = tipotransaccion;
        this.monto = monto;
        this.fecharegistro = fecharegistro;
        this.cuentabancaria = cuentabancaria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipotransaccion() {
        return tipotransaccion;
    }

    public void setTipotransaccion(String tipotransaccion) {
        this.tipotransaccion = tipotransaccion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public CuentaBancariaEntity getCuentabancaria() {
        return cuentabancaria;
    }

    public void setCuentabancaria(CuentaBancariaEntity cuentabancaria) {
        this.cuentabancaria = cuentabancaria;
    }
}
