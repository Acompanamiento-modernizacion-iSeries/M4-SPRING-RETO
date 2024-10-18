package co.bancolombia.aplicacionbancaria.services;
import co.bancolombia.aplicacionbancaria.models.Cuenta;
import co.bancolombia.aplicacionbancaria.models.CuentaBasica;
import co.bancolombia.aplicacionbancaria.models.CuentaPremium;
import co.bancolombia.aplicacionbancaria.models.dto.TransaccionDTO;
import co.bancolombia.aplicacionbancaria.models.Transaccion;
import co.bancolombia.aplicacionbancaria.repository.CuentaRepository;
import co.bancolombia.aplicacionbancaria.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class TransaccionesService {
    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;
    LocalDateTime currentTS = LocalDateTime.now();


    public TransaccionesService(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public Cuenta depositoSucursal(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = validaCuenta(transaccionDTO);
        cuenta = cuenta.DepositoSucursal(transaccionDTO.getMonto());
        Transaccion logHistoria = llenarTransaccion(cuenta, "Depósito en sucursal fisica", transaccionDTO.getMonto());
        cuenta.getHistoricoTransacciones().add(logHistoria);
        transaccionRepository.save(logHistoria);
        return cuenta;
    }

    public Cuenta depositoCajero(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = validaCuenta(transaccionDTO);
        if (cuenta instanceof CuentaBasica) {
            CuentaBasica cuentaBasica = (CuentaBasica) cuenta;
            cuentaBasica = (CuentaBasica) cuentaBasica.DepositoCajero(transaccionDTO.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaBasica, "Depósito desde cajero", transaccionDTO.getMonto());
            cuentaBasica.getHistoricoTransacciones().add(logHistoria);
            transaccionRepository.save(logHistoria);
            return cuentaBasica;
        } else {
            CuentaPremium cuentaPremium = (CuentaPremium) cuenta;
            cuentaPremium = (CuentaPremium) cuentaPremium.DepositoCajero(transaccionDTO.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaPremium, "Depósito desde cajero", transaccionDTO.getMonto());
            transaccionRepository.save(logHistoria);
            return cuentaPremium;
        }
    }

    public Cuenta transferencia(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = validaCuenta(transaccionDTO);
        if (cuenta instanceof CuentaBasica) {
            CuentaBasica cuentaBasica = (CuentaBasica) cuenta;
            cuentaBasica = (CuentaBasica) cuentaBasica.Transferencia(transaccionDTO.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaBasica, "Depósito desde otras cuentas", transaccionDTO.getMonto());
            cuentaBasica.getHistoricoTransacciones().add(logHistoria);
            transaccionRepository.save(logHistoria);
            return cuentaBasica;
        } else {
            CuentaPremium cuentaPremium = (CuentaPremium) cuenta;
            cuentaPremium = (CuentaPremium) cuentaPremium.Transferencia(transaccionDTO.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaPremium, "Depósito desde otras cuentas", transaccionDTO.getMonto());
            cuentaPremium.getHistoricoTransacciones().add(logHistoria);
            transaccionRepository.save(logHistoria);
            return cuentaPremium;
        }
    }

    public Cuenta compraFisico(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = validaCuenta(transaccionDTO);
        cuenta = cuenta.CompraFisico(transaccionDTO.getMonto());
        System.out.println("monto: " + transaccionDTO.getMonto());
        Transaccion logHistoria = llenarTransaccion(cuenta, "Depósito desde establecimiento fisico", transaccionDTO.getMonto());
        cuenta.getHistoricoTransacciones().add(logHistoria);
        transaccionRepository.save(logHistoria);
        return cuenta;
    }

    public Cuenta compraWeb(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = validaCuenta(transaccionDTO);
        cuenta = cuenta.CompraWeb(transaccionDTO.getMonto());
        Transaccion logHistoria = llenarTransaccion(cuenta, "Depósito desde pagina web", transaccionDTO.getMonto());
        cuenta.getHistoricoTransacciones().add(logHistoria);
        transaccionRepository.save(logHistoria);
        return cuenta;
    }

    public Cuenta retiroCajero(TransaccionDTO transaccionDTO) {
        Cuenta cuenta = validaCuenta(transaccionDTO);
        if (cuenta instanceof CuentaBasica) {
            CuentaBasica cuentaBasica = (CuentaBasica) cuenta;
            cuentaBasica = (CuentaBasica) cuentaBasica.RetiroCajero(transaccionDTO.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaBasica, "Retiro en cajero", transaccionDTO.getMonto());
            cuentaBasica.getHistoricoTransacciones().add(logHistoria);
            transaccionRepository.save(logHistoria);
            return cuentaBasica;
        } else {
            CuentaPremium cuentaPremium = (CuentaPremium) cuenta;
            cuentaPremium = (CuentaPremium) cuentaPremium.RetiroCajero(transaccionDTO.getMonto());
            Transaccion logHistoria = llenarTransaccion(cuentaPremium, "Retiro en cajero", transaccionDTO.getMonto());
            cuentaPremium.getHistoricoTransacciones().add(logHistoria);
            transaccionRepository.save(logHistoria);
            return cuentaPremium;
        }
    }

    public Cuenta validaCuenta(TransaccionDTO transaccion) {
        Cuenta OPtionaldatosCuenta = cuentaRepository.findById(transaccion.getIdCuenta()).orElseThrow(() ->
                new NoSuchElementException("Cuenta no encontrada"));

        Cuenta datosCuenta = OPtionaldatosCuenta;
        return datosCuenta;
    }

    public Transaccion llenarTransaccion(Cuenta datosCuenta, String tipo_transaccion, BigDecimal monto) {
        Transaccion historia = new Transaccion(null,
                tipo_transaccion,
                monto,
                Timestamp.valueOf(currentTS),
                datosCuenta);
        return historia;
    }
}
