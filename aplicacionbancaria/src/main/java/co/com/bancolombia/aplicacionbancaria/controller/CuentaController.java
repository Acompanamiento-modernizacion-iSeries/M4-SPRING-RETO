package co.com.bancolombia.aplicacionbancaria.controller;

import co.com.bancolombia.aplicacionbancaria.dto.CuentaDTO;
import co.com.bancolombia.aplicacionbancaria.dto.TransacEntreCuentasDTO;
import co.com.bancolombia.aplicacionbancaria.dto.TransaccionDTO;
import co.com.bancolombia.aplicacionbancaria.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    private final CuentaService CUENTA_SERVICE;

    public CuentaController(CuentaService cuentaService) {
        this.CUENTA_SERVICE = cuentaService;
    }

    @GetMapping("/consultaSaldo")
    public String consultaSaldo(@Valid @RequestBody CuentaDTO cuenta) {
        return "Su saldo actual es $"
                + CUENTA_SERVICE.consultarSaldo(cuenta);
    }

    @PostMapping("/depositoSucursal")
    public String depositoSucursal(@Valid @RequestBody TransaccionDTO transaccion) {
        BigDecimal nuevoSaldo = CUENTA_SERVICE.depositoSuc(transaccion);

        return "Deposito desde sucursal realizado con éxito! "
                + "- Nuevo saldo: $" + nuevoSaldo;
    }

    @PostMapping("/depositoCajeroAuto")
    public String depositoCajeroAuto(@Valid @RequestBody TransaccionDTO transaccion) {
        BigDecimal nuevoSaldo = CUENTA_SERVICE.depositoCajAuto(transaccion);

        return "Deposito desde cajero automatico realizado con éxito! "
                + "- Nuevo saldo: $" + nuevoSaldo;
    }

    @PostMapping("/depositoOtraCuenta")
    public String depositoOtraCuenta(@Valid @RequestBody TransacEntreCuentasDTO transaccion) {
        BigDecimal saldoCtaOrig = CUENTA_SERVICE.transferencia(transaccion);

        return "Transferencia entre cuentas realizado con éxito! "
                + "- Nuevo saldo: $" + saldoCtaOrig;
    }

    @PostMapping("/compraEstablecimiento")
    public String compraEstablecimiento(@Valid @RequestBody TransaccionDTO transaccion) {
        BigDecimal nuevoSaldo = CUENTA_SERVICE.compraFisic(transaccion);

        return "Compra en establecimiento realizada con éxito! "
                + "- Nuevo saldo: $" + nuevoSaldo;
    }

    @PostMapping("/compraWeb")
    public String compraWeb(@Valid @RequestBody TransaccionDTO transaccion) {
        BigDecimal nuevoSaldo = CUENTA_SERVICE.compraVirt(transaccion);

        return "Compra en web realizada con éxito! "
                + "- Nuevo saldo: $" + nuevoSaldo;
    }

    @PostMapping("/retiroCajero")
    public String retiroCajero(@Valid @RequestBody TransaccionDTO transaccion) {
        BigDecimal nuevoSaldo = CUENTA_SERVICE.retiroCaj(transaccion);

        return "Retiro desde cajero realizado con éxito! "
                + "- Nuevo saldo: $" + nuevoSaldo;
    }
}
