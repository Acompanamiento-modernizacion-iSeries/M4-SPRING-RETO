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

    //Crear una cuenta.
    @Transactional
    public Cuenta save(CreaCuentaDTO crearDTO) {
        //Verificar si la cuenta ya existe en la base de datos.
        Optional<Cuenta> cuentaExistente = cuentaRepository.findByNroCuenta(crearDTO.consultarNroCuenta());
        if (cuentaExistente.isPresent()) {
            throw new RuntimeException("¡La cuenta número " + crearDTO.consultarNroCuenta() + " ya existe!");
        }
        //Definir el tipo de cuenta a crear.
        Cuenta cuenta = null;
        if (crearDTO.consultarTipoCuenta().equals("BASICA"))
        {
            cuenta = new CuentaBasica();
        }
        else if (crearDTO.consultarTipoCuenta().equals("PREMIUM"))
        {
            cuenta = new CuentaPremium();
        }

        cuenta.asignarCuenta(crearDTO.consultarNroCuenta());
        cuenta.asignarSaldo(crearDTO.consultarSaldo());
        cuenta.asignarTitular(crearDTO.consultarTitular());
        cuenta.asignarDocumentoTitular(crearDTO.consultarDocumentoTitular());
        cuenta.asignarTelefono(crearDTO.consultarTelefono());
        cuenta.asignarDireccion(crearDTO.consultarDireccion());
        cuenta.asignarEmail(crearDTO.consultarEmail());

        //Grabar cuenta nueva en la base de datos.
        Cuenta cuentaNueva = cuentaRepository.save(cuenta);

        if (cuentaNueva == null) {
            throw new RuntimeException("¡No se pudo crear la cuenta!");
        }

        // Crear y guardar la transacción de cuenta creada.
        Transaccion transaccion = new Transaccion();

        transaccion.asignarCuentaAsociada(cuentaNueva);
        transaccion.asignarTipoTransaccion("Creación de cuenta.");
        transaccion.asignarValor(crearDTO.consultarSaldo());
        transaccion.asignarComision(BigDecimal.ZERO);
        transaccion.asignarFecha(LocalDate.now());
        transaccion.asignarHora(LocalTime.now());

        //Grabar transacción en la base de datos.
        transaccionRepository.save(transaccion);
        return cuentaNueva;
    }


    //Obtener saldo de una cuenta.
    @Transactional
    public Cuenta obtenerSaldo(ConsultaCuentaDTO consultaCuentaDTO) {
        //Buscar cuenta en la base de datos.
        Optional<Cuenta> cuentaEncontrada = cuentaRepository.findByNroCuenta(consultaCuentaDTO.getNroCuenta());
        if (cuentaEncontrada.isEmpty()) {
            throw new NoSuchElementException("¡La cuenta número " + consultaCuentaDTO.getNroCuenta() + " no existe!");
        }
        // Crear y guardar la transacción de consulta de saldo.
        Cuenta cuenta = cuentaEncontrada.get();
        Transaccion transaccion = new Transaccion();

        transaccion.asignarCuentaAsociada(cuenta);
        transaccion.asignarTipoTransaccion("Consulta de saldo.");
        transaccion.asignarValor(cuenta.consultarSaldo());
        transaccion.asignarComision(BigDecimal.ZERO);
        transaccion.asignarFecha(LocalDate.now());
        transaccion.asignarHora(LocalTime.now());

        transaccionRepository.save(transaccion);
        return cuenta;

    }

}
