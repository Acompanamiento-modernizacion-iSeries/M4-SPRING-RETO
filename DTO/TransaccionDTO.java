package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransaccionDTO {

    @NotNull(message = "Cuenta asociada es obligatoria!")
    @Positive(message = "Cuenta debe ser mayor a cero!")
    private Long cuentaAsociada;

    @NotNull(message = "Valor es obligatorio!")
    @Positive(message = "Valor debe ser mayor a cero!")
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
