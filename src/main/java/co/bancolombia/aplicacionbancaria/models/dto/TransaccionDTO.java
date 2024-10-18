package co.bancolombia.aplicacionbancaria.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransaccionDTO {
    @NotNull(message = "Debe ingresar el id de la  cuenta")
    @Positive(message = "El id cuenta debe ser mayor a cero")
    private Long idCuenta;

    @NotNull(message = "Debe ingresar el valor de la transacción")
    @Positive(message = "El valor debe ser mayor a cero")
    private BigDecimal monto;

    public TransaccionDTO() {
    }

    public TransaccionDTO(Long idCuenta, BigDecimal monto) {
        this.idCuenta = idCuenta;
        this.monto = monto;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}