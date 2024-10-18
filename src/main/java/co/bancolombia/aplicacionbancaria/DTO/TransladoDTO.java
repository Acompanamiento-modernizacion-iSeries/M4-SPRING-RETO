package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransladoDTO {

    @NotNull(message = "Indique la cuenta origen,  es obligatorio!")
    @Positive(message = "La cuenta origen no debe de ser cero!")
    private Long cuentaOrigen;

    @NotNull(message = "Indique la cuenta destino,  es obligatorio!")
    @Positive(message = "La cuenta destino no debe de ser cero!")
    private Long cuentaDestino;

    @NotNull(message = "Indique un valor,  es obligatorio!")
    @Positive(message = "El valor no debe de ser cero!")
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
