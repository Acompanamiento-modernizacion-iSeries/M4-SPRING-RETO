package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TrasladoDTO {

    @NotNull(message = "La cuenta origen es obligatoria!")
    @Positive(message = "La cuenta origen debe ser mayor a cero!")
    private Long cuentaOrigen;

    @NotNull(message = "La cuenta destino es obligatoria!")
    @Positive(message = "La cuenta destino debe ser mayor a cero!")
    private Long cuentaDestino;

    @NotNull(message = "El valor es obligatorio!")
    @Positive(message = "El valor debe ser mayor a cero!")
    private BigDecimal valor;

    public TrasladoDTO(Long cuentaOrigen, Long cuentaDestino, BigDecimal valor) {
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
