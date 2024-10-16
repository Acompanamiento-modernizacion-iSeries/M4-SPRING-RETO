package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class EntreCuentasDTO {
    @NotNull(message = "La cuenta de origen es obligatoria!")
    @Positive(message = "La cuenta de origen debe ser mayor a cero!")
    private Long cuentaOrigen;

    @NotNull(message = "La cuenta de destino es obligatoria!")
    @Positive(message = "La cuenta de destino debe ser mayor a cero!")
    private Long cuentaDestino;

    @NotNull(message = "El valor es obligatorio!")
    @Positive(message = "El valor debe ser mayor a cero!")
    private BigDecimal valor;

    public EntreCuentasDTO(Long cuentaOrigen, Long cuentaDestino, BigDecimal valor) {
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.valor = valor;
    }

    public Long getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Long getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
