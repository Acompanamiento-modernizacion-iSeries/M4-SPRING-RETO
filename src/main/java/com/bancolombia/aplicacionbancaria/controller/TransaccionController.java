package com.bancolombia.aplicacionbancaria.controller;

import com.bancolombia.aplicacionbancaria.config.GlobalExceptionHandler;
import com.bancolombia.aplicacionbancaria.cuenta.Cuenta;
import com.bancolombia.aplicacionbancaria.cuenta.CuentaRepository;
import com.bancolombia.aplicacionbancaria.transaccion.Transaccion;
import com.bancolombia.aplicacionbancaria.transaccion.TransaccionDTO;
import com.bancolombia.aplicacionbancaria.transaccion.TransaccionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/transacciones")
public class TransaccionController {
    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    @Autowired
    public TransaccionController(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @PostMapping("/deposito")
    public ResponseEntity<String> realizarDeposito(@RequestBody @Valid TransaccionDTO transaccionDTO) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(transaccionDTO.getNumeroCuenta())
                .orElseThrow(() -> new GlobalExceptionHandler.CuentaNoEncontradaException("Cuenta no encontrada"));
        double saldoActual = cuenta.depositar(transaccionDTO.getMonto(), transaccionDTO.getTipoOperacion());
        cuentaRepository.save(cuenta);
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo("Deposito");
        transaccion.setMonto(transaccionDTO.getMonto());
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setCuenta(cuenta);
        transaccionRepository.save(transaccion);
        return new ResponseEntity<>("Depósito realizado con éxito. Saldo actual: " + saldoActual, HttpStatus.OK);
    }

    @PostMapping("/retiro")
    public ResponseEntity<String> realizarRetiro(@RequestBody @Valid TransaccionDTO transaccionDTO) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(transaccionDTO.getNumeroCuenta())
                .orElseThrow(() -> new GlobalExceptionHandler.CuentaNoEncontradaException("Cuenta no encontrada"));
        double saldoActual = cuenta.retirar(transaccionDTO.getMonto());
        cuentaRepository.save(cuenta);
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo("Retiro");
        transaccion.setMonto(transaccionDTO.getMonto());
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setCuenta(cuenta);
        transaccionRepository.save(transaccion);
        return new ResponseEntity<>("Retiro realizado con éxito. Saldo actual: " + saldoActual, HttpStatus.OK);
    }

    @PostMapping("/compra")
    public ResponseEntity<String> realizarCompra(@RequestBody @Valid TransaccionDTO transaccionDTO, @RequestParam boolean esCompraWeb) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(transaccionDTO.getNumeroCuenta())
                .orElseThrow(() -> new GlobalExceptionHandler.CuentaNoEncontradaException("Cuenta no encontrada"));
        double saldoActual = cuenta.comprar(transaccionDTO.getMonto(), esCompraWeb);
        cuentaRepository.save(cuenta);
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(esCompraWeb ? "Compra en línea" : "Compra física");
        transaccion.setMonto(transaccionDTO.getMonto());
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setCuenta(cuenta);
        transaccionRepository.save(transaccion);
        return new ResponseEntity<>("Compra realizada con éxito. Saldo actual: " + saldoActual, HttpStatus.OK);
    }

    @GetMapping("/historial/{numeroCuenta}")
    public ResponseEntity<List<Transaccion>> obtenerHistorialTransacciones(@PathVariable String numeroCuenta) {
        List<Transaccion> transacciones = transaccionRepository.findTop5ByCuentaNumeroCuentaOrderByFechaDesc(numeroCuenta);
        if (transacciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }
}


