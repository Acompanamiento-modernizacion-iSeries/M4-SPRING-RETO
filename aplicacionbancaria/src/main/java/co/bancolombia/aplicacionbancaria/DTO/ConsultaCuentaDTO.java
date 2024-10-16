package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.*;

public class ConsultaCuentaDTO {

    @NotNull(message = "El número de cuenta es obligatorio!")
    @Positive(message = "El número de cuenta debe ser mayor a cero!")
    private Long nroCuenta;

    public ConsultaCuentaDTO(Long nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public ConsultaCuentaDTO() {
    }

    public Long getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(Long nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

}