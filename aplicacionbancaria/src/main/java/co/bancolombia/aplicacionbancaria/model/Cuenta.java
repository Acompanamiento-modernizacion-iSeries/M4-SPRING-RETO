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
    @Column(name = "id_cuenta")
    private Long id;

    @Column(name = "nro_cuenta")
    private Long nroCuenta;

    private BigDecimal saldo;
    private String titular;

    @Column(name = "documento_titular")
    private String documentoTitular;

    private String telefono;
    private String direccion;
    private String email;

    //Relación uno a muchos con la tabla Transaccion.
    @OneToMany(mappedBy = "cuentaAsociada")
    private List<Transaccion> transacciones;

    public Cuenta(Long nroCuenta, BigDecimal saldo, String titular, String documentoTitular, String telefono, String direccion, String email) {
        this.nroCuenta = nroCuenta;
        this.saldo = saldo;
        this.titular = titular;
        this.documentoTitular = documentoTitular;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
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

    public String consultarTitular() {

        return titular;
    }

    public void asignarTitular(String titular) {

        this.titular = titular;
    }

    public String consultarDocumentoTitular() {

        return documentoTitular;
    }

    public void asignarDocumentoTitular(String documentoTitular) {

        this.documentoTitular = documentoTitular;
    }

    public String consultarTelefono() {

        return telefono;
    }

    public void asignarTelefono(String telefono) {

        this.telefono = telefono;
    }

    public String consultarDireccion() {

        return direccion;
    }

    public void asignarDireccion(String direccion) {

        this.direccion = direccion;
    }

    public String consultarEmail() {

        return email;
    }

    public void asignarEmail(String email) {

        this.email = email;
    }

    public List<Transaccion> consultarTransacciones() {
        return transacciones;
    }

    public void asignarTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    //Metodos basicos para retiro y deposito.
    public void deposito(BigDecimal monto) {
        saldo = saldo.add(monto);
    }

    public void retiro(BigDecimal monto) {
        saldo = saldo.subtract(monto);
    }

    //Métodos abstractos que serán implementados en las clases hijas.
    public abstract void depositoSucursal(BigDecimal monto);
    public abstract void depositoDesdeCajero(BigDecimal monto);
    public abstract void depositoDesdeOtraCuenta(BigDecimal monto);
    public abstract void compraFisica(BigDecimal monto);
    public abstract void compraWeb(BigDecimal monto);
    public abstract void retiroCajero(BigDecimal monto);


    //Método sobreescrito para mostrar los datos de la cuenta.
    @Override
    public String toString() {
        return "{" + '\n' +
                "Titular= " + titular + '\n' +
                "Número de cuenta= " + nroCuenta + '\n' +
                "Saldo= " + saldo + '\n' +
                "Tipo de cuenta= " + this.getClass().getSimpleName() + '\n' +
                '}';
    }
}
