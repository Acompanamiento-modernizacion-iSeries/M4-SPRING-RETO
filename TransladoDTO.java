package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransladoDTO {

    @NotNull(message = "Cuenta origen es obligatoria")
    @Positive(message = "Cuenta origen debe ser mayor a cero")
    private Long cuentaOrigen;

    @NotNull(message = "Cuenta destino es obligatoria")
    @Positive(message = "Cuenta destino debe ser mayor a cero")
    private Long cuentaDestino;

    @NotNull(message = "Valor traslado es obligatorio")
    @Positive(message = "Valor debe ser mayor a cero")
    private BigDecimal valor;

    public TransladoDTO(Long cuentaOrigen, Long cuentaDestino, BigDecimal valor) {
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
