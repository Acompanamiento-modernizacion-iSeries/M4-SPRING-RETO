package co.bancolombia.aplicacionbancaria.controller;

import co.bancolombia.aplicacionbancaria.DTO.ConsultaCuentaDTO;
import co.bancolombia.aplicacionbancaria.DTO.EntreCuentasDTO;
import co.bancolombia.aplicacionbancaria.DTO.TransaccionDTO;
import co.bancolombia.aplicacionbancaria.model.Transaccion;
import co.bancolombia.aplicacionbancaria.service.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaccion")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService){
        this.transaccionService = transaccionService;
    }

    //Depósito desde sucursal.
    @PostMapping("/deposito/sucursal")
    public ResponseEntity<String> depositarDesdeSucursal(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Transaccion transaccion = transaccionService.depositarDesdeSucursal(transaccionDTO);
        String mensaje = String.format("Depósito desde sucursal realizado correctamente. Número de cuenta: %d, Comision :%.2f, Nuevo saldo: %.2f",
                transaccion.consultarCuentaAsociada().consultarCuenta(), transaccion.consultarComision(),transaccion.consultarCuentaAsociada().consultarSaldo());
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    //Depósito desde cajero.
    @PostMapping("/deposito/cajero")
    public ResponseEntity<String> depositar(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Transaccion transaccion = transaccionService.depositarDesdeCajero(transaccionDTO);
        String mensaje = String.format("Depósito desde cajero realizado correctamente. Número de cuenta: %d, Comision :%.2f, Nuevo saldo: %.2f",
                transaccion.consultarCuentaAsociada().consultarCuenta(), transaccion.consultarComision(),transaccion.consultarCuentaAsociada().consultarSaldo());
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    //Transferencia entre cuentas.
    @PostMapping("/transferencia")
    public ResponseEntity<String> transferirEntreCuentas(@Valid @RequestBody EntreCuentasDTO entreCuentasDTO) {
        Transaccion transaccion = transaccionService.transferirEntreCuentas(entreCuentasDTO);
        String mensaje = String.format("Transferencia entre cuentas realizada. Número de cuenta origen: %d, Número de cuenta destino: %d, Nuevo saldo destino: %.2f",
                entreCuentasDTO.getCuentaOrigen(), entreCuentasDTO.getCuentaDestino(), transaccion.consultarCuentaAsociada().consultarSaldo());
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    //Compra en establecimiento.
    @PostMapping("/compra/fisico")
    public ResponseEntity<String> comprarEnEstablecimiento(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Transaccion transaccion = transaccionService.comprarEnEstablecimiento(transaccionDTO);
        String mensaje = String.format("Compra en establecimiento realizada correctamente. Número de cuenta: %d, Comision :%.2f, Nuevo saldo: %.2f",
                transaccion.consultarCuentaAsociada().consultarCuenta(), transaccion.consultarComision(), transaccion.consultarCuentaAsociada().consultarSaldo());
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    //Compra en WEB.
    @PostMapping("/compra/web")
    public ResponseEntity<String> comprarEnWEB(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Transaccion transaccion = transaccionService.comprarWeb(transaccionDTO);
        String mensaje = String.format("Compra WEB realizada correctamente. Número de cuenta: %d,  Comision :%.2f, Nuevo saldo: %.2f",
                transaccion.consultarCuentaAsociada().consultarCuenta(), transaccion.consultarComision(), transaccion.consultarCuentaAsociada().consultarSaldo());
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    //Retiro en cajero.
    @PostMapping("/retiro/cajero")
    public ResponseEntity<String> retirarEnCajero(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        Transaccion transaccion = transaccionService.retirarDesdeCajero(transaccionDTO);
        String mensaje = String.format("Retiro desde cajero realizado correctamente. Número de cuenta: %d, Comision :%.2f, Nuevo saldo: %.2f",
                transaccion.consultarCuentaAsociada().consultarCuenta(), transaccion.consultarComision(), transaccion.consultarCuentaAsociada().consultarSaldo());
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @GetMapping("/transacciones/ultimas5")
    public ResponseEntity<String> obtenerUltimas5Transacciones(@Valid @RequestBody ConsultaCuentaDTO consultaCuentaDTO) {
        List<Transaccion> transacciones = transaccionService.obtenerUltimas5Transacciones(consultaCuentaDTO);
        String mensaje = "ULTIMAS 5 TRANSACCIONES:\n" + transacciones.toString();
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

}
