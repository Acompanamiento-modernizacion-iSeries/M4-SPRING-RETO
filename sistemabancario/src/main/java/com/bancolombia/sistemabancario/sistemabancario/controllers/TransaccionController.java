package com.bancolombia.sistemabancario.sistemabancario.controllers;


import com.bancolombia.sistemabancario.sistemabancario.models.Cuenta;
import com.bancolombia.sistemabancario.sistemabancario.models.DTO.TransaccionDTO;
import com.bancolombia.sistemabancario.sistemabancario.services.TransaccionesService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaccion")
public class TransaccionController {

   private final TransaccionesService transaccioService;

    public TransaccionController(TransaccionesService transaccioService) {
        this.transaccioService = transaccioService;
    }

    @PostMapping("/depositodesdesucursal")
    public String depositoDesdeSucursal(@Valid @RequestBody TransaccionDTO transaccion) {
        Cuenta datosCuenta = transaccioService.depositoDesdeSucursal(transaccion);
        return "Repósito realizado con exito\n" + datosCuenta.toString();
    }

    @PostMapping("/depositodesdecajero")
    public String depositoDesdeCajero(@Valid @RequestBody TransaccionDTO transaccion) {
        Cuenta datosCuenta = transaccioService.depositoDesdeCajero(transaccion);
        return "Repósito realizado con exito\n" + datosCuenta.toString();
    }

    @PostMapping("/depositodesdeotrascuentas")
    public String depositoDesdeOtrasCuentas(@Valid @RequestBody TransaccionDTO transaccion) {
        Cuenta datosCuenta = transaccioService.depositoDesdeOtrasCuentas(transaccion);
        return "Repósito realizado con exito\n" + datosCuenta.toString();
    }

    @PostMapping("/depositodesdeestablecimientofisico")
    public String depositoDesdeEstablecimientoFisico(@Valid @RequestBody TransaccionDTO transaccion) {
        Cuenta datosCuenta = transaccioService.depositoDesdeEstablecimientoFisico(transaccion);
        return "Repósito realizado con exito\n" + datosCuenta.toString();
    }

    @PostMapping("/depositodesdepaginaweb")
    public String depositoDesdePaginaWeb(@Valid @RequestBody TransaccionDTO transaccion) {
        Cuenta datosCuenta = transaccioService.depositoDesdePaginaWeb(transaccion);
        return "Repósito realizado con exito\n" + datosCuenta.toString();
    }

    @PostMapping("/retiroencajero")
    public String retiroEnCajero(@Valid @RequestBody TransaccionDTO transaccion) {
        Cuenta datosCuenta = transaccioService.RetiroEnCajero(transaccion);
        return "Retiro realizado con exito\n" + datosCuenta.toString();
    }



}
