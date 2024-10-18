package co.bancolombia.aplicacionbancaria.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CrearCuentaDTO {
    @NotNull(message = "tipo - El nombre del titular esta vacio")
    @Positive(message = "tipo - El dato ingresado es invalido")
    private Integer tipo;

    @NotNull(message = "saldo - El dato ingresado es invalido")
    @Positive(message = "saldo - El dato ingresado es invalido")
    private BigDecimal saldo;

    @NotNull(message = "Debe ingresar el id de la cuenta")
    @Positive(message = "El id de la cuenta debe ser mayor a cero")
    private Long idCuenta;

    public CrearCuentaDTO() {

    }

    public CrearCuentaDTO(Integer tipo, BigDecimal saldo, Long idCuenta) {
        this.tipo = tipo;
        this.saldo = saldo;
        this.idCuenta = idCuenta;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }
}
