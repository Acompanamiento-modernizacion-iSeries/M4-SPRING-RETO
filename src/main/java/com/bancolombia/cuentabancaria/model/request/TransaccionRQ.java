package com.bancolombia.cuentabancaria.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransaccionRQ {

    @NotNull(message = "El idCuenta no puede ser nula")
    private Long idCuenta;

    @NotNull(message = "El valor no puede ser nulo")
    @Positive(message = "El valor debe ser positivo")
    private BigDecimal valor;

    public TransaccionRQ(Long idCuenta, BigDecimal valor) {
        this.idCuenta = idCuenta;
        this.valor = valor;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
