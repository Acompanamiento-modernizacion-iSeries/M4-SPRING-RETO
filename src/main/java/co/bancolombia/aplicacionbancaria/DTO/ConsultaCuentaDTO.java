package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.*;

public class ConsultaCuentaDTO {

    @NotNull(message = "Indique la cuenta,  es obligatorio!")
    @Positive(message = "La cuenta debe no debe de ser cero!")
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