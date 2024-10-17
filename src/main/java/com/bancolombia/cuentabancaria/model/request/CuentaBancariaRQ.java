package com.bancolombia.cuentabancaria.model.request;

import jakarta.validation.constraints.NotNull;

public class CuentaBancariaRQ {

    @NotNull(message = "El idCuenta no puede ser nula")
    private Long idCuenta;

    public CuentaBancariaRQ(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public CuentaBancariaRQ() {
    }
}
