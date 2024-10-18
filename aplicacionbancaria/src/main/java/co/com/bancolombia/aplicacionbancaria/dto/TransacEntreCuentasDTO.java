package co.com.bancolombia.aplicacionbancaria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class TransacEntreCuentasDTO extends TransaccionDTO{

    @NotEmpty(message = "Debe ingresar un ID para la cuenta destino")
    @Min(message = "Debe ingresar un ID de cuenta destino válido", value = 1L)
    protected String idCuentaDestino;

    public TransacEntreCuentasDTO(String idCuenta, String idCuentaDestino, String monto, String descripcion) {
        super(idCuenta, monto, descripcion);
        this.idCuentaDestino = idCuentaDestino;
    }

    public Long idCuentaDestino() {
        return Long.valueOf(idCuentaDestino);
    }
}
