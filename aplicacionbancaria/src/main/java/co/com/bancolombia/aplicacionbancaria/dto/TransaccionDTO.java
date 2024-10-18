package co.com.bancolombia.aplicacionbancaria.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class TransaccionDTO {

    @NotEmpty(message = "Debe ingresar un ID de cuenta")
    @Min(message = "Debe ingresar un ID de cuenta válido", value = 1L)
    protected String idCuenta;

    @NotEmpty(message = "Debe ingresar un monto de transacción válido")
    @DecimalMin(message = "Debe ingresar un monto mayor o igual a $0.01", value = "0.01", inclusive = true)
    protected String monto;

    @NotEmpty(message = "Ingrese una descripción")
    protected String descripcion;

    public TransaccionDTO(String idCuenta, String monto, String descripcion) {
        this.idCuenta = idCuenta;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public Long idCuenta() {
        return Long.valueOf(idCuenta);
    }

    public BigDecimal monto() {
        return new BigDecimal(monto);
    }

    public String descripcion() {
        return descripcion;
    }

    public void cambiarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
