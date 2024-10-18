package co.bancolombia.aplicacionbancaria.service;

import co.bancolombia.aplicacionbancaria.DTO.ConsultaCuentaDTO;
import co.bancolombia.aplicacionbancaria.DTO.CreaCuentaDTO;
import co.bancolombia.aplicacionbancaria.model.Cuenta;
import co.bancolombia.aplicacionbancaria.model.CuentaBasica;
import co.bancolombia.aplicacionbancaria.model.CuentaPremium;
import co.bancolombia.aplicacionbancaria.model.Transaccion;
import co.bancolombia.aplicacionbancaria.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    public CuentaService(CuentaRepository cuentaRepository, TransaccionRepository transaccionRepository) {
        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Transactional
    public Cuenta save(CreaCuentaDTO crearDTO) {
        Optional<Cuenta> cuentaExistente = cuentaRepository.findByNroCuenta(crearDTO.consultarNroCuenta());
        if (cuentaExistente.isPresent()) {
            throw new RuntimeException("¡La cuenta número " + crearDTO.consultarNroCuenta() + " ya existe!");
        }

        Cuenta cuenta = null;
        if (crearDTO.consultarTipoCuenta().equals("BASICA"))
        {
            cuenta = new CuentaBasica(crearDTO.consultarNroCuenta(), crearDTO.consultarSaldo(), crearDTO.consultarTitular());
        }
        else if (crearDTO.consultarTipoCuenta().equals("PREMIUM"))
        {
            cuenta = new CuentaPremium(crearDTO.consultarNroCuenta(), crearDTO.consultarSaldo(), crearDTO.consultarTitular());
        }

        Cuenta cuentaNueva = cuentaRepository.save(cuenta);

        if (cuentaNueva == null) {
            throw new RuntimeException("¡No se pudo crear la cuenta!");
        }

        Transaccion transaccion = new Transaccion(cuentaNueva, "CREACION", cuentaNueva.consultarSaldo(), BigDecimal.ZERO,  LocalDate.now(), LocalTime.now());

        transaccionRepository.save(transaccion);

        return cuentaNueva;
    }

    public Cuenta obtenerSaldo(ConsultaCuentaDTO consultaCuentaDTO) {
          Optional<Cuenta> cuentaEncontrada = cuentaRepository.findByNroCuenta(consultaCuentaDTO.getNroCuenta());
          if (cuentaEncontrada.isEmpty() ) {
              throw new NoSuchElementException("¡La cuenta número "+ consultaCuentaDTO.getNroCuenta()+" no existe!");
          }
          Cuenta cuenta = cuentaEncontrada.get();

        Transaccion transaccion = new Transaccion(cuenta, "CONSULTA",cuenta.consultarSaldo(), BigDecimal.ZERO,  LocalDate.now(), LocalTime.now());
        transaccionRepository.save(transaccion);

        return cuenta;

    }

}
