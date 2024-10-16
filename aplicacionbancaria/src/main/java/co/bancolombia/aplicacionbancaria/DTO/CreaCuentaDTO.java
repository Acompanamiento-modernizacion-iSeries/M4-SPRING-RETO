package co.bancolombia.aplicacionbancaria.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreaCuentaDTO {
    @NotNull(message = "El número de cuenta es obligatorio!")
    @Positive(message = "El número de cuenta debe ser mayor a cero!")
    @Digits(integer = 12, fraction = 0, message = "El número de cuenta debe tener un máximo de 12 dígitos!")
    private Long nroCuenta;

    @NotEmpty(message = "El tipo de cuenta es obligatorio!")
    @Size(max = 50, message = "El tipo de cuenta debe tener un máximo de 50 caracteres!")
    @Pattern(regexp = "BASICA|PREMIUM", message = "El tipo de cuenta debe ser 'BASICA' o 'PREMIUM'!")
    private String tipoCuenta;

    @NotNull(message = "El saldo es obligatorio!")
    @Digits(integer = 13, fraction = 2, message = "El saldo debe tener un máximo de 15 dígitos en total, con 2 decimales!")
    @PositiveOrZero(message = "El saldo debe ser mayor o igual a cero!")
    private BigDecimal saldo;

    @NotEmpty(message = "El titular es obligatorio!")
    @Size(max = 255, message = "El titular debe tener un máximo de 255 caracteres!")
    private String titular;

    @NotEmpty(message = "El documento del titular es obligatorio!")
    @Size(max = 50, message = "El documento del titular debe tener un máximo de 50 caracteres!")
    private String documentoTitular;

    @NotEmpty(message = "El teléfono es obligatorio!")
    @Size(max = 20, message = "El teléfono debe tener un máximo de 20 caracteres!")
    private String telefono;

    @NotEmpty(message = "La dirección es obligatoria!")
    @Size(max = 255, message = "La dirección debe tener un máximo de 255 caracteres!")
    private String direccion;

    @NotEmpty(message = "El email es obligatorio!")
    @Size(max = 80, message = "El email debe tener un máximo de 80 caracteres!")
    @Email(message = "El email debe ser válido!")
    private String email;

    public CreaCuentaDTO(Long nroCuenta, String tipoCuenta, BigDecimal saldo, String titular, String documentoTitular, String telefono, String direccion, String email) {
        this.nroCuenta = nroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.titular = titular;
        this.documentoTitular = documentoTitular;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    public Long consultarNroCuenta() {return nroCuenta;}

    public void asignarNroCuenta(Long nroCuenta) { this.nroCuenta = nroCuenta; }

    public String consultarTipoCuenta() { return tipoCuenta; }

    public void asignarTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public BigDecimal consultarSaldo() {
        return saldo;
    }

    public void asignarSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String consultarTitular() {
        return titular;
    }

    public void asignarTitular(String titular) {
        this.titular = titular;
    }

    public String consultarDocumentoTitular() {
        return documentoTitular;
    }

    public void asignarDocumentoTitular(String documentoTitular) {
        this.documentoTitular = documentoTitular;
    }

    public String consultarTelefono() {
        return telefono;
    }

    public void asignarTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String consultarDireccion() {
        return direccion;
    }

    public void asignarDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String consultarEmail() {
        return email;
    }

    public void asignarEmail(String email) {
        this.email = email;
    }
}
