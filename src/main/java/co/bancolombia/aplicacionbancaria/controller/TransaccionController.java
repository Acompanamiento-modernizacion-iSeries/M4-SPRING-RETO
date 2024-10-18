package co.bancolombia.aplicacionbancaria.controller;

import co.bancolombia.aplicacionbancaria.DTO.ConsultaCuentaDTO;
import co.bancolombia.aplicacionbancaria.DTO.TransaccionDTO;
import co.bancolombia.aplicacionbancaria.DTO.TrasladoDTO;
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

    @PostMapping("/depositocajeroelectronico")
    public String depositoCajero(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta cuenta = transaccionService.depositoCajero(transaccionDTO);
        String mensaje = "Déposito desde cajero realizado exitosamente, " + cuenta.toString();
        return mensaje;
    }

    @PostMapping("/comprasfisico")
    public String compraFisico(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta cuenta = transaccionService.compraFisico(transaccionDTO);
        String mensaje = "Compra física realizada exitosamente, " + cuenta.toString();
        return mensaje;
    }

    @PostMapping("/comprasweb")
    public String compraWeb(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta cuenta = transaccionService.compraWeb(transaccionDTO);
        String mensaje = "Compra WEB realizada exitosamente, " + cuenta.toString();
        return mensaje;
    }

    @PostMapping("/retirocajeroelectronico")
    public String retiroCajero(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Cuenta cuenta = transaccionService.retiroCajero(transaccionDTO);
        String mensaje = "Retiro en Cajero realizado exitosamente, " + cuenta.toString();
        return mensaje;
    }

    @PostMapping("/trasladocuentas")
    public String transladoCuentas(@Valid @RequestBody TrasladoDTO trasladoDTO) {
        Cuenta cuenta = transaccionService.transladoCuentas(trasladoDTO);
        String mensaje = "Translado entre cuentas realizado exitosamente.  Cuenta origen: "
                +trasladoDTO.getCuentaOrigen()+ " Cuenta destino: "+ trasladoDTO.getCuentaDestino() + " "
                + "Nuevo saldo cuenta destino: " + cuenta.consultarSaldo();
        return mensaje;
    }

    @GetMapping("/historialtransacciones")
    public String historialTransacciones(@Valid @RequestBody ConsultaCuentaDTO consultaCuentaDTO){
        List<Transaccion> transacciones = transaccionService.historialTransacciones(consultaCuentaDTO);
        String mensaje = "Historial últimas 5 transacciones: \n" + transacciones.toString();
        return mensaje;
    }


}
