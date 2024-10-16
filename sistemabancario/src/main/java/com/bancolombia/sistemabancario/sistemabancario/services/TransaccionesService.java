package com.bancolombia.sistemabancario.sistemabancario.services;

import com.bancolombia.sistemabancario.sistemabancario.models.Cuenta;
import com.bancolombia.sistemabancario.sistemabancario.models.CuentaBasica;
import com.bancolombia.sistemabancario.sistemabancario.models.CuentaPremium;
import com.bancolombia.sistemabancario.sistemabancario.models.DTO.TransaccionDTO;
import com.bancolombia.sistemabancario.sistemabancario.models.Transaccion;
import com.bancolombia.sistemabancario.sistemabancario.repository.CuentaRepository;
import com.bancolombia.sistemabancario.sistemabancario.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;


@Service
public class TransaccionesService {
    private final TransaccionRepository historiaRepository;
    private final CuentaRepository cuentaRepository;
    LocalDateTime currentTS = LocalDateTime.now();



    public TransaccionesService(TransaccionRepository historiaRepository, CuentaRepository cuentaRepository) {
        this.historiaRepository = historiaRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public Cuenta depositoDesdeSucursal(TransaccionDTO transaccion){
        Cuenta cuenta = ValidaCuenta(transaccion);
        cuenta = cuenta.DepositoDesdeSucursal(transaccion.getMonto());
        Transaccion logHistoria = llenarTransaccion(cuenta, "Depósito en sucursal fisica", transaccion.getMonto());
        cuenta.getHistoricoTransacciones().add(logHistoria);
        historiaRepository.save(logHistoria);
        return cuenta;
    }

    public Cuenta depositoDesdeCajero(TransaccionDTO transaccion){
        Cuenta cuenta = ValidaCuenta(transaccion);
        if(cuenta instanceof  CuentaBasica) {
            CuentaBasica cuentaBasica = (CuentaBasica)cuenta;
            cuentaBasica = (CuentaBasica) cuentaBasica.DepositoDesdecajeroAutomatico(transaccion.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaBasica, "Depósito desde cajero", transaccion.getMonto());
            cuentaBasica.getHistoricoTransacciones().add(logHistoria);
            historiaRepository.save(logHistoria);
            return cuentaBasica;
        }else{
            CuentaPremium cuentaPremium = (CuentaPremium) cuenta;
            cuentaPremium = (CuentaPremium) cuentaPremium.DepositoDesdecajeroAutomatico(transaccion.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaPremium, "Depósito desde cajero", transaccion.getMonto());
            historiaRepository.save(logHistoria);
            return cuentaPremium;
        }
    }

    public Cuenta depositoDesdeOtrasCuentas(TransaccionDTO transaccion){
        Cuenta cuenta = ValidaCuenta(transaccion);
        if(cuenta instanceof  CuentaBasica) {
            CuentaBasica cuentaBasica = (CuentaBasica)cuenta;
            cuentaBasica = (CuentaBasica) cuentaBasica.DepositoDesdeOtrasCuentas(transaccion.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaBasica, "Depósito desde otras cuentas", transaccion.getMonto());
            cuentaBasica.getHistoricoTransacciones().add(logHistoria);
            historiaRepository.save(logHistoria);
            return cuentaBasica;
        }else{
            CuentaPremium cuentaPremium = (CuentaPremium) cuenta;
            cuentaPremium = (CuentaPremium) cuentaPremium.DepositoDesdeOtrasCuentas(transaccion.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaPremium, "Depósito desde otras cuentas", transaccion.getMonto());
            cuentaPremium.getHistoricoTransacciones().add(logHistoria);
            historiaRepository.save(logHistoria);
            return cuentaPremium;
        }

    }

    public Cuenta depositoDesdeEstablecimientoFisico(TransaccionDTO transaccion){
        Cuenta cuenta = ValidaCuenta(transaccion);
        cuenta =  cuenta.CompraEnEstablecimientoFisico(transaccion.getMonto());
        Transaccion logHistoria = llenarTransaccion(cuenta, "Depósito desde establecimiento fisico", transaccion.getMonto());
        cuenta.getHistoricoTransacciones().add(logHistoria);
        historiaRepository.save(logHistoria);
        return cuenta;
    }

    public Cuenta depositoDesdePaginaWeb(TransaccionDTO transaccion){
        Cuenta cuenta = ValidaCuenta(transaccion);
        cuenta =  cuenta.CompraEnpaginaWeb(transaccion.getMonto());
        Transaccion logHistoria = llenarTransaccion(cuenta, "Depósito desde pagina web", transaccion.getMonto());
        cuenta.getHistoricoTransacciones().add(logHistoria);
        historiaRepository.save(logHistoria);
        return cuenta;
    }

    public Cuenta RetiroEnCajero(TransaccionDTO transaccion){
        Cuenta cuenta = ValidaCuenta(transaccion);
        if(cuenta instanceof  CuentaBasica) {
            CuentaBasica cuentaBasica = (CuentaBasica)cuenta;
            cuentaBasica = (CuentaBasica) cuentaBasica.DepositoDesdecajeroAutomatico(transaccion.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaBasica, "Retiro en cajero", transaccion.getMonto());
            cuentaBasica.getHistoricoTransacciones().add(logHistoria);
            historiaRepository.save(logHistoria);
            return cuentaBasica;
        }else{
            CuentaPremium cuentaPremium = (CuentaPremium) cuenta;
            cuentaPremium = (CuentaPremium) cuentaPremium.DepositoDesdecajeroAutomatico(transaccion.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaPremium, "Retiro en cajero", transaccion.getMonto());
            cuentaPremium.getHistoricoTransacciones().add(logHistoria);
            historiaRepository.save(logHistoria);
            return cuentaPremium;
        }
    }

    public Cuenta ValidaCuenta(TransaccionDTO transaccion){
        Cuenta OPtionaldatosCuenta = cuentaRepository.findById(transaccion.getIdCuenta()).orElseThrow(() ->
                new NoSuchElementException("Cuenta no encontrada"));

        Cuenta datosCuenta = OPtionaldatosCuenta;
        return datosCuenta;
    }


    public Transaccion llenarTransaccion(Cuenta datosCuenta , String tipo_transaccion, BigDecimal monto){
        Transaccion historia = new Transaccion(null,
                tipo_transaccion,
                monto,
                Timestamp.valueOf(currentTS),
                datosCuenta);
        return historia;
    }



}
