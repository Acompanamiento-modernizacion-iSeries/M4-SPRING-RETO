package co.bancolombia.aplicacionbancaria.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long idTransaccion;

    //Relación muchos a uno con la tabla Cuenta.
    @ManyToOne
    @JoinColumn(name = "cuenta_asociada")
    private Cuenta cuentaAsociada;

    @Column(name = "tipo_transaccion")
    private String tipoTransaccion;

    private BigDecimal valor;

    private BigDecimal comision;

    private LocalDate fecha;

    private LocalTime hora;

    public Transaccion(Cuenta cuentaAsociada, String tipoTransaccion, BigDecimal valor, BigDecimal comision, LocalDate fecha, LocalTime hora) {
        this.cuentaAsociada = cuentaAsociada;
        this.tipoTransaccion = tipoTransaccion;
        this.valor = valor;
        this.comision = comision;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Transaccion() {
    }

    public Long consultarTransaccion() {
        return idTransaccion;
    }

    public void asignarTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Cuenta consultarCuentaAsociada() {
        return cuentaAsociada;
    }

    public void asignarCuentaAsociada(Cuenta cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }

    public String consultarTipoTransaccion() {
        return tipoTransaccion;
    }

    public void asignarTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public BigDecimal consultarValor() {
        return valor;
    }

    public void asignarValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal consultarComision() {
        return comision;
    }

    public void asignarComision(BigDecimal comision) {
        this.comision = comision;
    }

    public LocalDate consultarFecha() {
        return fecha;
    }

    public void asignarFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime consultarHora() {
        return hora;
    }

    public void asignarHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Transaccion{\n" +
                " Id Transacción = " + idTransaccion +
                "\n Número Cuenta = " + cuentaAsociada.consultarCuenta() +
                "\n Tipo Transaccion = '" + tipoTransaccion + '\'' +
                "\n Valor = " + valor +
                "\n Comisión = " + comision +
                "\n Fecha = " + fecha +
                "\n Hora = " + hora +
                "\n } \n";
    }
}