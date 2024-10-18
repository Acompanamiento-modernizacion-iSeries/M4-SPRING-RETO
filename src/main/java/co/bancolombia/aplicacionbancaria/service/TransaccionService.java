package co.bancolombia.aplicacionbancaria.service;

import co.bancolombia.aplicacionbancaria.DTO.ConsultaCuentaDTO;
import co.bancolombia.aplicacionbancaria.DTO.TransaccionDTO;
import co.bancolombia.aplicacionbancaria.DTO.TransladoDTO;
import co.bancolombia.aplicacionbancaria.model.Cuenta;
import co.bancolombia.aplicacionbancaria.model.CuentaBasica;
import co.bancolombia.aplicacionbancaria.model.Transaccion;
import co.bancolombia.aplicacionbancaria.repository.CuentaRepository;
import co.bancolombia.aplicacionbancaria.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    public TransaccionService(TransaccionRepository repository, CuentaRepository cuentaRepository) {
        this.transaccionRepository = repository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public Cuenta depositoSucursal(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = cuentaRepository.findByNroCuenta(transaccionDTO.consultarCuentaAsociada())
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada"));

        cuenta.depositoSucursal(transaccionDTO.consultarValor());
        Transaccion transaccion = new Transaccion(cuenta, "DÉPOSITO DESDE SUCURSAL", transaccionDTO.consultarValor(), BigDecimal.ZERO,  LocalDate.now(), LocalTime.now());
        transaccionRepository.save(transaccion);

        return cuenta;
    }

    @Transactional
    public Cuenta depositoCajero(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = cuentaRepository.findByNroCuenta(transaccionDTO.consultarCuentaAsociada())
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada"));

        if (cuenta instanceof CuentaBasica && transaccionDTO.consultarValor().compareTo(BigDecimal.valueOf(2))<=0) {
            throw new IllegalArgumentException("El valor de depósito en cajero debe ser mayor a 2");
        }
        BigDecimal comision;
        if (cuenta instanceof CuentaBasica) {
            comision = BigDecimal.valueOf(2);
        } else {
            comision = BigDecimal.ZERO;
        }
        cuenta.depositoCajero(transaccionDTO.consultarValor());
        Transaccion transaccion = new Transaccion(cuenta, "DÉPOSITO DESDE CAJERO", transaccionDTO.consultarValor(), comision ,  LocalDate.now(), LocalTime.now());
        transaccionRepository.save(transaccion);

        return cuenta;
    }

    @Transactional
    public Cuenta compraFisico(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = cuentaRepository.findByNroCuenta(transaccionDTO.consultarCuentaAsociada())
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada"));

        if (cuenta.consultarSaldo().compareTo(transaccionDTO.consultarValor()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        cuenta.compraFisica(transaccionDTO.consultarValor());
        Transaccion transaccion = new Transaccion(cuenta, "COMPRA ", transaccionDTO.consultarValor(), BigDecimal.ZERO,  LocalDate.now(), LocalTime.now());
        transaccionRepository.save(transaccion);

        return cuenta;
    }

    @Transactional
    public Cuenta compraWeb(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = cuentaRepository.findByNroCuenta(transaccionDTO.consultarCuentaAsociada())
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada"));

        if (cuenta.consultarSaldo().compareTo(transaccionDTO.consultarValor().add(BigDecimal.valueOf(5))) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        cuenta.compraWeb(transaccionDTO.consultarValor());
        Transaccion transaccion = new Transaccion(cuenta, "COMPRA WEB", transaccionDTO.consultarValor(), BigDecimal.valueOf(5),  LocalDate.now(), LocalTime.now());
        transaccionRepository.save(transaccion);

        return cuenta;
    }

    @Transactional
    public Cuenta retiroCajero(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = cuentaRepository.findByNroCuenta(transaccionDTO.consultarCuentaAsociada())
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada"));

        if (cuenta instanceof CuentaBasica && transaccionDTO.consultarValor().add(BigDecimal.valueOf(1)).compareTo(cuenta.consultarSaldo()) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente para retiro desde cajero.");
        }
        BigDecimal comision;
        if (cuenta instanceof CuentaBasica) {
            comision = BigDecimal.valueOf(1);
        } else {
            comision = BigDecimal.ZERO;
        }

        cuenta.retiroCajero(transaccionDTO.consultarValor());
        Transaccion transaccion = new Transaccion(cuenta, "RETIRO CAJERO", transaccionDTO.consultarValor(), comision,  LocalDate.now(), LocalTime.now());
        transaccionRepository.save(transaccion);

        return cuenta;
    }

    @Transactional
    public Cuenta transladoCuentas(TransladoDTO transladoDTO) {
        if (transladoDTO.getCuentaOrigen().equals(transladoDTO.getCuentaDestino())) {
            throw new IllegalArgumentException("Las cuentas origen y destino no pueden ser iguales");
        }

        Cuenta cuentaOrigen = cuentaRepository.findByNroCuenta(transladoDTO.getCuentaOrigen())
                .orElseThrow(() -> new NoSuchElementException("Cuenta origen no encontrada"));
        Cuenta cuentaDestino = cuentaRepository.findByNroCuenta(transladoDTO.getCuentaDestino())
                .orElseThrow(() -> new NoSuchElementException("Cuenta destino no encontrada"));

        if (cuentaOrigen.consultarSaldo().compareTo(transladoDTO.getValor().add(BigDecimal.valueOf(1.5))) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente en cuenta origen");
        }

        cuentaOrigen.retiro(transladoDTO.getValor().add(BigDecimal.valueOf(1.5)));
        Transaccion transaccionOrigen = new Transaccion(cuentaOrigen, "TRANSFERENCIA A CUENTA: "+transladoDTO.getCuentaDestino(), transladoDTO.getValor(), BigDecimal.valueOf(1.5),  LocalDate.now(), LocalTime.now());
        transaccionRepository.save(transaccionOrigen);

        cuentaDestino.deposito(transladoDTO.getValor());
        Transaccion transaccionDestino = new Transaccion(cuentaDestino, "TRANSFERENCIA DESDE CUENTA: "+transladoDTO.getCuentaOrigen(), transladoDTO.getValor(), BigDecimal.ZERO,  LocalDate.now(), LocalTime.now());
        transaccionRepository.save(transaccionDestino);

        return cuentaDestino;

    }

    @Transactional
    public List<Transaccion> historialTransacciones(ConsultaCuentaDTO consultaCuentaDTO) {
        Cuenta cuenta = cuentaRepository.findByNroCuenta(consultaCuentaDTO.getNroCuenta())
                .orElseThrow(() -> new NoSuchElementException("Cuenta no encontrada"));

        return transaccionRepository.findTop5ByNroCuenta(consultaCuentaDTO.getNroCuenta());
    }


}



