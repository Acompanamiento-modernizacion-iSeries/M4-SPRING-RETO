package com.bancolombia.cuentabancaria.controller;

import com.bancolombia.cuentabancaria.model.request.TransaccionHistoricoRQ;
import com.bancolombia.cuentabancaria.service.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/transaccion")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping(path = "/historico")
    public ResponseEntity<Object> saldo(@Valid @RequestBody TransaccionHistoricoRQ rq){
        Map<String, Object> message = new HashMap<>();
        message.put("historicoTransaccion", transaccionService.historicoTransaccion(rq.getIdCuenta()));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
