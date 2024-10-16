package com.bancolombia.sistemabancario.sistemabancario.models.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ConsultaPorIdCuentaDTO {

    @NotNull(message = "Debe ingresar el id de la cuenta")
    @Positive(message = "El id de la cuenta debe ser mayor a cero")
    private Long idCuenta;

    public ConsultaPorIdCuentaDTO() {
    }

    public ConsultaPorIdCuentaDTO(Long idCuenta) {

        this.idCuenta = idCuenta;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {

        this.idCuenta = idCuenta;
    }
}
