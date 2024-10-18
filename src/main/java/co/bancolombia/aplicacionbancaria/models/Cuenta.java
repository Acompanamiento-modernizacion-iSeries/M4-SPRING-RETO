package co.bancolombia.aplicacionbancaria.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.INTEGER)
@Table(name="cuenta")
public abstract class Cuenta {

    public static final BigDecimal VALOR_SEGURO = BigDecimal.valueOf(5.0);
    public static final BigDecimal VALOR_TRANSFERENCIA = BigDecimal.valueOf(1.5);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcuenta")
    protected Long CuentaId;

    @Column(name = "cuenta")
    protected Integer numeroCuenta;

    protected BigDecimal saldo;
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference

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

    public Cuenta DepositoSucursal(BigDecimal valorDeposito){
        this.setSaldo(getSaldo().add(valorDeposito));
        return this;
    }

    abstract public Cuenta DepositoCajero(BigDecimal valorDeposito);

    public Cuenta Transferencia(BigDecimal valorDeposito){
        if(this.saldo.compareTo(valorDeposito) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir el costo del depósito");
        }
        this.setSaldo(getSaldo().subtract(VALOR_TRANSFERENCIA));
        this.setSaldo(getSaldo().add(valorDeposito));
        return this;
    }

    public Cuenta CompraFisico(BigDecimal ValorCompra){
        if(this.saldo.compareTo(ValorCompra) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir la compra");
        }
        this.setSaldo(getSaldo().subtract(ValorCompra));
        return this;
    }

    public Cuenta CompraWeb(BigDecimal valorCompra){
        BigDecimal ValorCompraMasSeguro = new BigDecimal(0);
        ValorCompraMasSeguro = ValorCompraMasSeguro.add(valorCompra).add(VALOR_SEGURO);
        if(this.saldo.compareTo(ValorCompraMasSeguro) != 1) {
            throw new IllegalArgumentException("El saldo es insuficiente para cubrir el valor de compra más el seguro");
        }
        this.setSaldo(getSaldo().subtract(ValorCompraMasSeguro));
        return this;
    }

   // abstract void RetiroEnCajero(BigDecimal valorRetiro);
   public abstract Cuenta RetiroCajero(BigDecimal valorRetiro);
    @Override
    public String toString() {
        return "Id     : " + CuentaId + "\nCuenta : " + numeroCuenta + "\nSaldo  : " + saldo ;
    }
}