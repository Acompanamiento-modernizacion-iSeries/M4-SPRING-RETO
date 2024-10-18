package co.com.bancolombia.aplicacionbancaria.service;

import co.com.bancolombia.aplicacionbancaria.dto.CuentaDTO;
import co.com.bancolombia.aplicacionbancaria.dto.TransacEntreCuentasDTO;
import co.com.bancolombia.aplicacionbancaria.dto.TransaccionDTO;
import co.com.bancolombia.aplicacionbancaria.model.Cuenta;
import co.com.bancolombia.aplicacionbancaria.model.CuentaBasica;
import co.com.bancolombia.aplicacionbancaria.model.CuentaPremium;
import co.com.bancolombia.aplicacionbancaria.model.Transaccion;
import co.com.bancolombia.aplicacionbancaria.repository.CuentaRepository;
import co.com.bancolombia.aplicacionbancaria.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CuentaService {

    private final CuentaRepository CUENTAS_DB;
    private final TransaccionRepository TRANSACCIONES_DB;

    public CuentaService(CuentaRepository cuentasDB, TransaccionRepository transaccionesDB) {
        this.CUENTAS_DB = cuentasDB;
        this.TRANSACCIONES_DB = transaccionesDB;
    }

    public BigDecimal consultarSaldo(CuentaDTO cuentaDTO) {
        return buscarCuenta(cuentaDTO.id()).saldo();
    }

    public BigDecimal depositoSuc(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = buscarCuenta(transaccionDTO.idCuenta());

        switch (cuenta.tipoCuenta()) {
            case "Cuenta Basica" -> ((CuentaBasica) cuenta).depositoSucursal(transaccionDTO.monto());
            case "Cuenta Premium" -> ((CuentaPremium) cuenta).depositoSucursal(transaccionDTO.monto());
            default -> throw new IllegalArgumentException("Tipo de cuenta desconocida: " + cuenta.tipoCuenta());
        }
        CUENTAS_DB.save(cuenta);

        agregarTransaccion(
                transaccionDTO,
                "deposito",
                cuenta
        );
        return cuenta.saldo();
    }

    public BigDecimal depositoCajAuto(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = buscarCuenta(transaccionDTO.idCuenta());

        switch (cuenta.tipoCuenta()) {
            case "Cuenta Basica" -> ((CuentaBasica) cuenta).depositoCajeroAuto(transaccionDTO.monto());
            case "Cuenta Premium" -> ((CuentaPremium) cuenta).depositoCajeroAuto(transaccionDTO.monto());
            default -> throw new IllegalArgumentException("Tipo de cuenta desconocida: " + cuenta.tipoCuenta());
        }
        CUENTAS_DB.save(cuenta);

        agregarTransaccion(
                transaccionDTO,
                "deposito",
                cuenta
        );
        return cuenta.saldo();
    }

    public BigDecimal transferencia(TransacEntreCuentasDTO transaccionDTO) {
        Cuenta cuentaOrigen = buscarCuenta(transaccionDTO.idCuenta());
        Cuenta cuentaDestino = buscarCuenta(transaccionDTO.idCuentaDestino());

        switch (cuentaOrigen.tipoCuenta()) {
            case "Cuenta Basica" -> ((CuentaBasica) cuentaOrigen).retiroOtraCta(transaccionDTO.monto());
            case "Cuenta Premium" -> ((CuentaPremium) cuentaOrigen).retiroOtraCta(transaccionDTO.monto());
            default -> throw new IllegalArgumentException("Tipo de cuenta origen desconocida: " + cuentaOrigen.tipoCuenta());
        }

        switch (cuentaDestino.tipoCuenta()) {
            case "Cuenta Basica" -> ((CuentaBasica) cuentaDestino).depositoOtraCta(transaccionDTO.monto());
            case "Cuenta Premium" -> ((CuentaPremium) cuentaDestino).depositoOtraCta(transaccionDTO.monto());
            default -> throw new IllegalArgumentException("Tipo de cuenta destino desconocida: " + cuentaOrigen.tipoCuenta());
        }

        CUENTAS_DB.save(cuentaOrigen);
        agregarTransaccion(
                transaccionDTO,
                "retiro",
                cuentaOrigen
        );

        CUENTAS_DB.save(cuentaDestino);
        transaccionDTO.cambiarDescripcion("Deposito desde otra cuenta");
        agregarTransaccion(
                transaccionDTO,
                "deposito",
                cuentaDestino
        );

        return cuentaOrigen.saldo();
    }

    public BigDecimal compraFisic(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = buscarCuenta(transaccionDTO.idCuenta());

        switch (cuenta.tipoCuenta()) {
            case "Cuenta Basica" -> ((CuentaBasica) cuenta).compraFisica(transaccionDTO.monto());
            case "Cuenta Premium" -> ((CuentaPremium) cuenta).compraFisica(transaccionDTO.monto());
            default -> throw new IllegalArgumentException("Tipo de cuenta desconocida: " + cuenta.tipoCuenta());
        }
        CUENTAS_DB.save(cuenta);

        agregarTransaccion(
                transaccionDTO,
                "compra",
                cuenta
        );
        return cuenta.saldo();
    }

    public BigDecimal compraVirt(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = buscarCuenta(transaccionDTO.idCuenta());

        switch (cuenta.tipoCuenta()) {
            case "Cuenta Basica" -> ((CuentaBasica) cuenta).compraVirtual(transaccionDTO.monto());
            case "Cuenta Premium" -> ((CuentaPremium) cuenta).compraVirtual(transaccionDTO.monto());
            default -> throw new IllegalArgumentException("Tipo de cuenta desconocida: " + cuenta.tipoCuenta());
        }
        CUENTAS_DB.save(cuenta);

        agregarTransaccion(
                transaccionDTO,
                "compra",
                cuenta
        );
        return cuenta.saldo();
    }

    public BigDecimal retiroCaj(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = buscarCuenta(transaccionDTO.idCuenta());

        switch (cuenta.tipoCuenta()) {
            case "Cuenta Basica" -> ((CuentaBasica) cuenta).retiroCajero(transaccionDTO.monto());
            case "Cuenta Premium" -> ((CuentaPremium) cuenta).retiroCajero(transaccionDTO.monto());
            default -> throw new IllegalArgumentException("Tipo de cuenta desconocida: " + cuenta.tipoCuenta());
        }
        CUENTAS_DB.save(cuenta);

        agregarTransaccion(
                transaccionDTO,
                "retiro",
                cuenta
        );
        return cuenta.saldo();
    }

    public Cuenta buscarCuenta(Long idCuenta) {
        Optional<Cuenta> cuentaSelect = CUENTAS_DB.findById(idCuenta);
        if (cuentaSelect.isEmpty())
            throw new NoSuchElementException("No existe una cuenta con el id proporcionado");

        return cuentaSelect.get();
    }

    public void agregarTransaccion(TransaccionDTO transaccionDTO, String tipoTransacc, Cuenta cuenta) {
        TRANSACCIONES_DB.save(new Transaccion(
                cuenta,
                tipoTransacc,
                transaccionDTO.monto(),
                transaccionDTO.descripcion(),
                new Date()
        ));
    }
}
