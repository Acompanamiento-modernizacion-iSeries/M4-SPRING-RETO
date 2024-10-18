package co.bancolombia.aplicacionbancaria.controllers;

import co.bancolombia.aplicacionbancaria.models.Cuenta;
import co.bancolombia.aplicacionbancaria.models.dto.TransaccionDTO;
import co.bancolombia.aplicacionbancaria.services.TransaccionesService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/transaccion")
public class TransaccionController {

    private final TransaccionesService transaccioService;

    public TransaccionController(TransaccionesService transaccioService) {
        this.transaccioService = transaccioService;
    }

    @PostMapping("/depositosucursal")
    public String depositoSucursal(@Valid @RequestBody TransaccionDTO TransaccionDTO) {
        Cuenta datosCuenta = transaccioService.depositoSucursal(TransaccionDTO);
        return "Depósito exitoso!!! \n" + datosCuenta.toString();
    }

    @PostMapping("/depositocajero")
    public String depositoCajero(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta datosCuenta = transaccioService.depositoCajero(transaccionDTO);
        return "Depósito exitoso!!!\n" + datosCuenta.toString();
    }

    @PostMapping("/transferencia")
    public String transferencia(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta datosCuenta = transaccioService.transferencia(transaccionDTO);
        return "Transferencia exitosa!!!\n" + datosCuenta.toString();
    }

    @PostMapping("/comprafisico")
    public String depositoFisico(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta datosCuenta = transaccioService.compraFisico(transaccionDTO);
        return "Retiro exitoso!!!\n" + datosCuenta.toString();
    }

    @PostMapping("/compraweb")
    public String depositoWeb(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta datosCuenta = transaccioService.compraWeb(transaccionDTO);
        return "Depósito exitoso!!!\n" + datosCuenta.toString();
    }

    @PostMapping("/retiroencajero")
    public String retiroCajero(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta datosCuenta = transaccioService.retiroCajero(transaccionDTO);
        return "Depósito exitoso!!!\n" + datosCuenta.toString();
    }
}