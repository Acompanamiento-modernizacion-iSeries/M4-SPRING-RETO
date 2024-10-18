package co.com.bancolombia.aplicacionbancaria.controller;

import co.com.bancolombia.aplicacionbancaria.dto.CuentaDTO;
import co.com.bancolombia.aplicacionbancaria.service.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaccion")
public class TransaccionController {

    private final TransaccionService TRANSACCION_SERVICE;

    public TransaccionController(TransaccionService transaccion_service) {
        TRANSACCION_SERVICE = transaccion_service;
    }

    @GetMapping("/ultimasTransacciones")
    public String ultimasTransacciones(@Valid @RequestBody CuentaDTO cuenta) {
        return TRANSACCION_SERVICE.consultaTransacciones(cuenta).toString();
    }
}
