package com.bancolombia.aplicacionbancaria.controller;
import com.bancolombia.aplicacionbancaria.cuenta.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaController(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearCuenta(@RequestBody @Valid CuentaDTO cuentaDTO) {
        Cuenta cuenta;
        if ("Básica".equalsIgnoreCase(cuentaDTO.getTipoCuenta())) {
            cuenta = new CuentaBasica(cuentaDTO.getNumeroCuenta(), cuentaDTO.getSaldoInicial());
        } else if ("Premium".equalsIgnoreCase(cuentaDTO.getTipoCuenta())) {
            cuenta = new CuentaPremium(cuentaDTO.getNumeroCuenta(), cuentaDTO.getSaldoInicial());
        } else {
            return new ResponseEntity<>("Tipo de cuenta no válido", HttpStatus.BAD_REQUEST);
        }
        cuentaRepository.save(cuenta);
        return new ResponseEntity<>("Cuenta creada con éxito", HttpStatus.CREATED);
    }
}



