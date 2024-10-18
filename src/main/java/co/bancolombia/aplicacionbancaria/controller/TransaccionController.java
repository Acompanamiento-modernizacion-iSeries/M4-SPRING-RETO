package co.bancolombia.aplicacionbancaria.controller;

import co.bancolombia.aplicacionbancaria.DTO.ConsultaCuentaDTO;
import co.bancolombia.aplicacionbancaria.DTO.TransaccionDTO;
import co.bancolombia.aplicacionbancaria.DTO.TransladoDTO;
import co.bancolombia.aplicacionbancaria.model.Cuenta;
import co.bancolombia.aplicacionbancaria.model.Transaccion;
import co.bancolombia.aplicacionbancaria.service.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaccion")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService){
        this.transaccionService = transaccionService;
    }

    @PostMapping("/depositosucursal")
    public String depositoSucursal(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta cuenta = transaccionService.depositoSucursal(transaccionDTO);
        String mensaje = "Déposito desde sucursal realizado exitosamente, " + cuenta.toString();
        return mensaje;
    }

    @PostMapping("/depositocajero")
    public String depositoCajero(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta cuenta = transaccionService.depositoCajero(transaccionDTO);
        String mensaje = "Déposito desde cajero realizado exitosamente, " + cuenta.toString();
        return mensaje;
    }

    @PostMapping("/comprafisico")
    public String compraFisico(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta cuenta = transaccionService.compraFisico(transaccionDTO);
        String mensaje = "Compra realizada exitosamente, " + cuenta.toString();
        return mensaje;
    }

    @PostMapping("/compraweb")
    public String compraWeb(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta cuenta = transaccionService.compraWeb(transaccionDTO);
        String mensaje = "Compra WEB realizada exitosamente, " + cuenta.toString();
        return mensaje;
    }

    @PostMapping("/retirocajero")
    public String retiroCajero(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta cuenta = transaccionService.retiroCajero(transaccionDTO);
        String mensaje = "Retiro en Cajero realizado exitosamente, " + cuenta.toString();
        return mensaje;
    }

    @PostMapping("/translado")
    public String transladoCuentas(@Valid @RequestBody TransladoDTO transladoDTO) {
        Cuenta cuenta = transaccionService.transladoCuentas(transladoDTO);
        String mensaje = "Translado entre cuentas realizado exitosamente.  "
                + " Cuenta destino: "+ transladoDTO.getCuentaDestino() + " "
                + "Nuevo saldo cuenta destino: " + cuenta.consultarSaldo();
        return mensaje;
    }

    @GetMapping("/historial")
    public String historialTransacciones(@Valid @RequestBody ConsultaCuentaDTO consultaCuentaDTO){
        List<Transaccion> transacciones = transaccionService.historialTransacciones(consultaCuentaDTO);
        String mensaje = "Historial últimas 5 transacciones: \n" + transacciones.toString();
        return mensaje;
    }


}
