package com.bancolombia.cuentabancaria.model.request;

import jakarta.validation.constraints.NotNull;

public class TransaccionHistoricoRQ {

    @NotNull(message = "El idCuenta no puede ser nula")
    private Long idCuenta;

    public TransaccionHistoricoRQ(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public TransaccionHistoricoRQ() {
    }
}
