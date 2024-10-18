package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreaCuentaDTO {
    @NotNull(message = "Número de cuenta es obligatorio")
    @Positive(message = "Número de cuenta debe ser mayor a cero")
    private Long nroCuenta;

    @NotNull(message = "Saldo debe ser obligatorio")
    @Positive(message = "Saldo debe ser mayor a cero")
    private BigDecimal saldo;

    @NotEmpty(message = "Nombre del titular es obligatorio")
    @Size(max = 100, message = "Nombre del titular debe tener un máximo de 100 caracteres")
    private String nombreCliente;

    @NotEmpty(message = "Tipo de cuenta es obligatorio!")
    @Pattern(regexp = "BASICA|PREMIUM", message = "El tipo de cuenta debe ser 'BASICA' o 'PREMIUM'")
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
