package co.com.bancolombia.aplicacionbancaria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class CuentaDTO {

    @NotEmpty(message = "Debe ingresar un ID de cuenta")
    @Min(message = "Debe ingresar un ID de cuenta válido", value = 1L)
    protected String id;

    @Min(message = "Para consultar transacciones de la cuenta, debe ingresar un numero igual o mayor a 1", value = 1L)
    protected String numTransacc;

    public CuentaDTO(String id, String numTransacc) {
        this.id = id;
        this.numTransacc = numTransacc == null ? "5" : numTransacc;
    }

    public Long id() {
        return Long.valueOf(id);
    }

    public Integer numTransacc() {
        return Integer.valueOf(numTransacc);
    }
}
