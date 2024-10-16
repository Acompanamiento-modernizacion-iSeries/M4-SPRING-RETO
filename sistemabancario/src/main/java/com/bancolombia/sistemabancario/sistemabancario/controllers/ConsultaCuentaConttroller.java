package com.bancolombia.sistemabancario.sistemabancario.controllers;


import com.bancolombia.sistemabancario.sistemabancario.models.Cuenta;
import com.bancolombia.sistemabancario.sistemabancario.models.DTO.ConsultaPorIdCuentaDTO;
import com.bancolombia.sistemabancario.sistemabancario.models.Transaccion;
import com.bancolombia.sistemabancario.sistemabancario.repository.TransaccionRepository;
import com.bancolombia.sistemabancario.sistemabancario.services.ConsultaCuentaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cuenta")
public class ConsultaCuentaConttroller {

    private final ConsultaCuentaService cuentaService;
    private final TransaccionRepository transacciones;

    public ConsultaCuentaConttroller(ConsultaCuentaService cuentaService, TransaccionRepository transacciones) {
        this.cuentaService = cuentaService;
        this.transacciones = transacciones;
    }


    @GetMapping("/saldo")
    public String saldo(@Valid @RequestBody ConsultaPorIdCuentaDTO consultaCuenta) {
        Cuenta datosCuenta = cuentaService.ConsultaCuenta(consultaCuenta);
        return datosCuenta.toString();
    }

    @GetMapping("/transacciones")
    public List<Transaccion> listaTrasacciones(@Valid @RequestBody ConsultaPorIdCuentaDTO consultaCuenta){
        return cuentaService.consultaHistoriaTransacciones(consultaCuenta);
    }


}
