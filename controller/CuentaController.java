package co.bancolombia.aplicacionbancaria.controller;

import co.bancolombia.aplicacionbancaria.DTO.ConsultaCuentaDTO;
import co.bancolombia.aplicacionbancaria.DTO.CreaCuentaDTO;
import co.bancolombia.aplicacionbancaria.model.Cuenta;
import co.bancolombia.aplicacionbancaria.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cuentabancaria")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping("/crearcuenta")
    public ResponseEntity<String> crearCuenta(@Valid @RequestBody CreaCuentaDTO crearDTO) {
        Cuenta cuenta = cuentaService.save(crearDTO);
        return new ResponseEntity<>("Creacion de cuenta exitosa. Cuenta : "+cuenta.consultarCuenta() , HttpStatus.CREATED);
    }

    @GetMapping("/consultarsaldo")
    public ResponseEntity<String> obtenerSaldo(@Valid @RequestBody ConsultaCuentaDTO consultaCuentaDTO) {
        Cuenta cuenta = cuentaService.obtenerSaldo(consultaCuentaDTO);
        return new ResponseEntity<>(cuenta.toString(), HttpStatus.OK);
    }

}
