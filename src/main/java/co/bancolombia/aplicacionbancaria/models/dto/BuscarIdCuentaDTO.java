package co.bancolombia.aplicacionbancaria.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BuscarIdCuentaDTO {

    @NotNull(message = "Debe ingresar el id de la cuenta")
    @Positive(message = "El id de la cuenta debe ser mayor a cero")
    private Long idCuenta;

    public BuscarIdCuentaDTO() {
    }

    public BuscarIdCuentaDTO(Long idCuenta) {

        this.idCuenta = idCuenta;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {

        this.idCuenta = idCuenta;
    }
}