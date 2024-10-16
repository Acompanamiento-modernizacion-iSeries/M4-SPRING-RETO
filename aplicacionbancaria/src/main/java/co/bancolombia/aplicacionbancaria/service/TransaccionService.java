package co.bancolombia.aplicacionbancaria.service;

import co.bancolombia.aplicacionbancaria.DTO.ConsultaCuentaDTO;
import co.bancolombia.aplicacionbancaria.DTO.EntreCuentasDTO;
import co.bancolombia.aplicacionbancaria.DTO.TransaccionDTO;
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

    //Método para obtener la cuenta asociada a la transacción.
    private Cuenta obtenerCuenta(Long cuentaAsociada) {
        return cuentaRepository.findByNroCuenta(cuentaAsociada)
                .orElseThrow(() -> new NoSuchElementException("¡Cuenta número: " +cuentaAsociada+ " NO encontrada!"));
    }

    //Método para realizar un depósito desde la sucursal.
    @Transactional
    public Transaccion depositarDesdeSucursal(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = obtenerCuenta(transaccionDTO.consultarCuentaAsociada());
        cuenta.depositoSucursal(transaccionDTO.consultarValor());

        Transaccion transaccion = new Transaccion();
        transaccion.asignarCuentaAsociada(cuenta);
        transaccion.asignarTipoTransaccion("Depósito desde sucursal.");
        transaccion.asignarValor(transaccionDTO.consultarValor());
        transaccion.asignarComision(BigDecimal.ZERO);
        transaccion.asignarFecha(LocalDate.now());
        transaccion.asignarHora(LocalTime.now());

        return transaccionRepository.save(transaccion);
    }

    //Método para realizar un depósito desde el cajero.
    @Transactional
    public Transaccion depositarDesdeCajero(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = obtenerCuenta(transaccionDTO.consultarCuentaAsociada());

        //Validar si el depósito cubre la comisión de 2000 para cuentas básicas.
        if (cuenta instanceof CuentaBasica && transaccionDTO.consultarValor().compareTo(BigDecimal.valueOf(2000)) <= 0) {
            throw new IllegalArgumentException("El depósito debe cubrir la comisión de $2000 para cuentas básicas");
        }
        cuenta.depositoDesdeCajero(transaccionDTO.consultarValor());

        Transaccion transaccion = new Transaccion();
        transaccion.asignarCuentaAsociada(cuenta);
        transaccion.asignarTipoTransaccion("Depósito desde cajero.");
        transaccion.asignarValor(transaccionDTO.consultarValor());
        transaccion.asignarComision(cuenta instanceof CuentaBasica ? BigDecimal.valueOf(2000) : BigDecimal.ZERO);
        transaccion.asignarFecha(LocalDate.now());
        transaccion.asignarHora(LocalTime.now());

        return transaccionRepository.save(transaccion);
    }

    //Método para realizar transferencia entre cuentas.
    @Transactional
    public Transaccion transferirEntreCuentas(EntreCuentasDTO entreCuentasDTO) {
        //Las cuentas no pueden ser iguales.
        if (entreCuentasDTO.getCuentaOrigen().equals(entreCuentasDTO.getCuentaDestino())) {
            throw new IllegalArgumentException("Las cuentas origen y destino no pueden ser iguales.");
        }

        //Se valida la cuenta origen.
        Cuenta cuentaOrigen = obtenerCuenta(entreCuentasDTO.getCuentaOrigen());
        //Se valida la cuenta destino.
        Cuenta cuentaDestino = obtenerCuenta(entreCuentasDTO.getCuentaDestino());

        //Validar si el valor a enviar mas comision se tiene en la cuenta origen.
        if (cuentaOrigen.consultarSaldo().compareTo(entreCuentasDTO.getValor().add(BigDecimal.valueOf(1500))) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la transferencia entre cuentas." +
                    " Debe tener en cuenta el valor a transferir: $" +entreCuentasDTO.getValor()+" más la comisión de $1500."+
                    " Saldo actual: $"+cuentaOrigen.consultarSaldo());
        }

        //Se realiza un retiro en la cuenta origen.
        cuentaOrigen.retiro(entreCuentasDTO.getValor().add(BigDecimal.valueOf(1500)));

        //Se graba transaccion realizada.
        Transaccion transaccion1 = new Transaccion();
        transaccion1.asignarCuentaAsociada(cuentaOrigen);
        transaccion1.asignarTipoTransaccion("Depósito a cuenta:" + entreCuentasDTO.getCuentaDestino());
        transaccion1.asignarValor(entreCuentasDTO.getValor());
        transaccion1.asignarComision(BigDecimal.valueOf(1500));
        transaccion1.asignarFecha(LocalDate.now());
        transaccion1.asignarHora(LocalTime.now());

        transaccionRepository.save(transaccion1);

        //Se realiza un depósito en la cuenta destino.
        cuentaDestino.deposito(entreCuentasDTO.getValor());

        //Se graba transaccion realizada.
        Transaccion transaccion2 = new Transaccion();
        transaccion2.asignarCuentaAsociada(cuentaDestino);
        transaccion2.asignarTipoTransaccion("Depósito recibido desde cuenta: "+ entreCuentasDTO.getCuentaOrigen());
        transaccion2.asignarValor(entreCuentasDTO.getValor());
        transaccion2.asignarComision(BigDecimal.ZERO);
        transaccion2.asignarFecha(LocalDate.now());
        transaccion2.asignarHora(LocalTime.now());

        return transaccionRepository.save(transaccion2);
    }

    //Método para realizar compra en establecimiento.
    @Transactional
    public Transaccion comprarEnEstablecimiento(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = obtenerCuenta(transaccionDTO.consultarCuentaAsociada());

        //Validar si el saldo es suficiente para realizar la compra.
        if (cuenta.consultarSaldo().compareTo(transaccionDTO.consultarValor()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la compra en establecimiento físico."+
                    " Saldo actual: $"+cuenta.consultarSaldo() + " Valor de la compra: $"+transaccionDTO.consultarValor());
        }

        cuenta.compraFisica(transaccionDTO.consultarValor());

        Transaccion transaccion = new Transaccion();
        transaccion.asignarCuentaAsociada(cuenta);
        transaccion.asignarTipoTransaccion("Compra en establecimiento físico.");
        transaccion.asignarValor(transaccionDTO.consultarValor());
        transaccion.asignarComision(BigDecimal.ZERO);
        transaccion.asignarFecha(LocalDate.now());
        transaccion.asignarHora(LocalTime.now());

        return transaccionRepository.save(transaccion);
    }

    //Método para realizar compra WEB.
    @Transactional
    public Transaccion comprarWeb(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = obtenerCuenta(transaccionDTO.consultarCuentaAsociada());

        //Validar si el saldo es suficiente para realizar la compra incluyendo la comisión.
        if (cuenta.consultarSaldo().compareTo(transaccionDTO.consultarValor().add(BigDecimal.valueOf(5000))) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la compra en establecimiento web."+
                    " Saldo actual: $"+cuenta.consultarSaldo() + " Valor de la compra: $"+transaccionDTO.consultarValor()+
                    " Comisión: $5000");
        }

        cuenta.compraWeb(transaccionDTO.consultarValor());

        Transaccion transaccion = new Transaccion();
        transaccion.asignarCuentaAsociada(cuenta);
        transaccion.asignarTipoTransaccion("Compra WEB.");
        transaccion.asignarValor(transaccionDTO.consultarValor());
        transaccion.asignarComision(BigDecimal.valueOf(5000));
        transaccion.asignarFecha(LocalDate.now());
        transaccion.asignarHora(LocalTime.now());

        return transaccionRepository.save(transaccion);
    }

    //Método para realizar retiro desde cajero.
    @Transactional
    public Transaccion retirarDesdeCajero(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = obtenerCuenta(transaccionDTO.consultarCuentaAsociada());

        //Validar si el retiro cubre la comisión de 1000 para cuentas básicas.
        if (cuenta instanceof CuentaBasica && transaccionDTO.consultarValor().compareTo(BigDecimal.valueOf(1000)) <= 0) {
            throw new IllegalArgumentException("El retiro debe cubrir la comisión de $1000 para cuentas básicas");
        }

        //Validar si el saldo es suficiente para realizar el retiro.
        if (cuenta.consultarSaldo().compareTo(transaccionDTO.consultarValor()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar el retiro desde cajero."+
                    " Saldo actual: $"+cuenta.consultarSaldo() + " Valor del retiro: $"+transaccionDTO.consultarValor()+
                    (cuenta instanceof CuentaBasica ? " Comisión: $1000" : ""));
        }

        cuenta.retiroCajero(transaccionDTO.consultarValor());

        Transaccion transaccion = new Transaccion();
        transaccion.asignarCuentaAsociada(cuenta);
        transaccion.asignarTipoTransaccion("Retiro desde cajero.");
        transaccion.asignarValor(transaccionDTO.consultarValor());
        transaccion.asignarComision(cuenta instanceof CuentaBasica ? BigDecimal.valueOf(1000) : BigDecimal.ZERO);
        transaccion.asignarFecha(LocalDate.now());
        transaccion.asignarHora(LocalTime.now());

        return transaccionRepository.save(transaccion);
    }

    //Método para consultar las últimas 5 transacciones de una cuenta.
    @Transactional(readOnly = true)
    public List<Transaccion> obtenerUltimas5Transacciones(ConsultaCuentaDTO consultaCuentaDTO) {
        Cuenta cuenta = obtenerCuenta(consultaCuentaDTO.getNroCuenta());
        return transaccionRepository.findTop5ByNroCuenta(consultaCuentaDTO.getNroCuenta());
    }

}



