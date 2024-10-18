package co.com.bancolombia.aplicacionbancaria.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cuenta")
public abstract class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String numero;
    protected String titular;
    protected BigDecimal saldo;

    @OneToMany(mappedBy = "cuenta")
    @OrderBy("fecha DESC")
    protected List<Transaccion> listaTransacciones;

    public Cuenta() { }

    public Cuenta(Long id, String numero, String titular, BigDecimal saldo) {
        this.id = id;
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
    }

    public List<Transaccion> transacciones() {
        return listaTransacciones;
    }

    public abstract String tipoCuenta();

    public BigDecimal saldo() {
        return saldo;
    }

    public boolean deposito(BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0)
            return false;

        saldo = saldo.add(monto);
        return true;
    }

    public boolean retiro(BigDecimal monto) {
        if (monto.compareTo(saldo) > 0)
            return false;

        saldo = saldo.subtract(monto);
        return true;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                '}';
    }
}
