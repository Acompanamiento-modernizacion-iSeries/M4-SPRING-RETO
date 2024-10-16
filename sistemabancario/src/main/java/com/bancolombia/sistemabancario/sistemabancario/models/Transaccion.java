package com.bancolombia.sistemabancario.sistemabancario.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name="historial_transacciones")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaccion_id")
    private Long transaccionid;
    @Column(name = "tipo_transaccion")
    private String tipotransaccion;
    @Column(name = "monto_transaccion")
    private BigDecimal montotransaccion;
    @Column(name = "fecha_transaccion")
    private Timestamp fechatransaccion;

    @ManyToOne
    @JoinColumn(name = "id_cuenta")
    @JsonBackReference
    private Cuenta cuenta;

    public Transaccion() {
    }

    public Transaccion(Long transaccionid, String tipotransaccion, BigDecimal montotransaccion, Timestamp fechatransaccion, Cuenta cuenta) {
        this.transaccionid = transaccionid;
        this.tipotransaccion = tipotransaccion;
        this.montotransaccion = montotransaccion;
        this.fechatransaccion = fechatransaccion;
        this.cuenta = cuenta;
    }

    public Long getTransaccionid() {
        return transaccionid;
    }

    public void setTransaccionid(Long transaccionid) {
        this.transaccionid = transaccionid;
    }

    public String getTipotransaccion() {
        return tipotransaccion;
    }

    public void setTipotransaccion(String tipotransaccion) {
        this.tipotransaccion = tipotransaccion;
    }

    public BigDecimal getValortransaccion() {
        return montotransaccion;
    }

    public void setValortransaccion(BigDecimal valortransaccion) {
        this.montotransaccion = valortransaccion;
    }

    public Timestamp getFechatransaccion() {
        return fechatransaccion;
    }

    public void setFechatransaccion(Timestamp fechatransaccion) {
        this.fechatransaccion = fechatransaccion;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
