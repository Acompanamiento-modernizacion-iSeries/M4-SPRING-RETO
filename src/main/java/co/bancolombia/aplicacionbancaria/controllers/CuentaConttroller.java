package co.bancolombia.aplicacionbancaria.controllers;

import co.bancolombia.aplicacionbancaria.models.Cuenta;
import co.bancolombia.aplicacionbancaria.models.dto.BuscarIdCuentaDTO;
import co.bancolombia.aplicacionbancaria.models.Transaccion;
import co.bancolombia.aplicacionbancaria.repository.TransaccionRepository;
import co.bancolombia.aplicacionbancaria.services.CuentaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuenta")
public class CuentaConttroller {

    private final CuentaService cuentaService;
    private final TransaccionRepository transaccionRepository;

    public CuentaConttroller(CuentaService cuentaService, TransaccionRepository transaccionRepository) {
        this.cuentaService = cuentaService;
        this.transaccionRepository = transaccionRepository;
    }

    @GetMapping("/saldo")
    public String saldo(@Valid @RequestBody BuscarIdCuentaDTO buscarIdCuentaDTO) {
        Cuenta cuenta = cuentaService.ConsultaCuenta(buscarIdCuentaDTO);
        return cuenta.toString();
    }

    @GetMapping("/transacciones")
    public List<Transaccion> listaTrasacciones(@Valid @RequestBody BuscarIdCuentaDTO buscarIdCuentaDTO){
        return cuentaService.consultaHistoriaTransacciones(buscarIdCuentaDTO);
    }
}
