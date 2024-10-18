package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransaccionDTO {

    @NotNull(message = "Indique la cuenta, es obligatoria!")
    @Positive(message = "La cuenta asociada no debe de ser cero!")
    private Long cuentaAsociada;

    @NotNull(message = "Indique un valor,  es obligatorio!")
    @Positive(message = "El valor no debe de ser cero!")
    private BigDecimal valor;

    public TransaccionDTO(Long cuentaAsociada, BigDecimal valor) {
        this.cuentaAsociada = cuentaAsociada;
        this.valor = valor;
    }

    public Long consultarCuentaAsociada() {
        return cuentaAsociada;
    }

    public void asignarCuentaAsociada(Long cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }

    public BigDecimal consultarValor() {
        return valor;
    }

    public void asignarValor(BigDecimal valor) {
        this.valor = valor;
    }
}
