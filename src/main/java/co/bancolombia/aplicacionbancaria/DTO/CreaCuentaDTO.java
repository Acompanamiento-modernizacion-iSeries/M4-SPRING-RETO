package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreaCuentaDTO {
    @NotNull(message = "Indique el número de cuenta,  es obligatorio!")
    @Positive(message = "El número de cuenta no debe de ser cero!")
    private Long nroCuenta;

    @NotNull(message = "Indique el valor del saldo,  es obligatorio!")
    @Positive(message = "El saldo debe no debe de ser cero!")
    private BigDecimal saldo;

    @NotEmpty(message = "El nombre del titular es obligatorio!")
    @Size(max = 100, message = "El nombre del titular debe tener un máximo de 100 caracteres!")
    private String nombreCliente;

    @NotEmpty(message = "El tipo de cuenta es obligatorio!")
    @Pattern(regexp = "BASICA|PREMIUM", message = "El tipo de cuenta debe ser 'BASICA' o 'PREMIUM'!")
    private String tipoCuenta;

    public CreaCuentaDTO(Long nroCuenta, BigDecimal saldo, String nombreCliente, String tipoCuenta) {
        this.nroCuenta = nroCuenta;
        this.saldo = saldo;
        this.nombreCliente = nombreCliente;
        this.tipoCuenta = tipoCuenta;
    }

    public Long consultarNroCuenta() {
        return nroCuenta;
    }

    public void asignarNroCuenta(Long nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public BigDecimal consultarSaldo() {
        return saldo;
    }

    public void asignarSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String consultarTitular() {
        return nombreCliente;
    }

    public void asignarTitular(String titular) {
        this.nombreCliente = titular;
    }

    public String consultarTipoCuenta() {
        return tipoCuenta;
    }

    public void asignarTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

}
