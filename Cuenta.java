package co.bancolombia.aplicacionbancaria.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cuenta_bancaria")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cuenta", discriminatorType = DiscriminatorType.STRING)
public abstract class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long idCuenta;

    @Column(name = "nro_cuenta")
    private Long nroCuenta;

    private BigDecimal saldo;

    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @OneToMany(mappedBy = "cuentaAsociada")
    private List<Transaccion> transacciones;

    public Cuenta(Long nroCuenta, BigDecimal saldo, String nombreCliente) {
        this.nroCuenta = nroCuenta;
        this.saldo = saldo;
        this.nombreCliente = nombreCliente;
    }

    public Cuenta() {
    }

    public Long consultarCuenta() {

        return nroCuenta;
    }

    public void asignarCuenta(Long nroCuenta) {

        this.nroCuenta = nroCuenta;
    }

    public BigDecimal consultarSaldo() {

        return saldo;
    }

    public void asignarSaldo(BigDecimal saldo) {

        this.saldo = saldo;
    }

    public String consultarNombre() {

        return nombreCliente;
    }

    public void asignarNombre(String titular) {

        this.nombreCliente = titular;
    }

    public List<Transaccion> consultarTransacciones() {
        return transacciones;
    }

    public void asignarTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public void deposito(BigDecimal monto) {
        saldo = saldo.add(monto);
    }

    public void retiro(BigDecimal monto) {
        saldo = saldo.subtract(monto);
    }

    //Métodos abstractos.
    public abstract void depositoSucursal(BigDecimal monto);
    public abstract void depositoCajero(BigDecimal monto);
    public abstract void depositoOtraCuenta(BigDecimal monto);
    public abstract void compraFisica(BigDecimal monto);
    public abstract void compraWeb(BigDecimal monto);
    public abstract void retiroCajero(BigDecimal monto);

    @Override
    public String toString() {
        return "{" + '\n' +
                "Titular         = " + nombreCliente + '\n' +
                "Número cuenta   = " + nroCuenta + '\n' +
                "Saldo           = " + saldo + '\n' +
                '}';
    }
}
