package co.com.bancolombia.aplicacionbancaria.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    protected Cuenta cuenta;

    protected String tipoTransacc;
    protected BigDecimal monto;
    protected String descripcion;
    protected Date fecha;

    public Transaccion() { }

    public Transaccion(Cuenta cuenta, String tipoTransacc, BigDecimal monto, String descripcion, Date fecha) {
        this.cuenta = cuenta;
        this.tipoTransacc = tipoTransacc;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "\nTransaccion{" +
                "id=" + id +
                ", cuenta=" + cuenta +
                ", tipoTransacc='" + tipoTransacc + '\'' +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
