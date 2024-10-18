package co.com.bancolombia.aplicacionbancaria.service;

import co.com.bancolombia.aplicacionbancaria.dto.CuentaDTO;
import co.com.bancolombia.aplicacionbancaria.dto.TransaccionDTO;
import co.com.bancolombia.aplicacionbancaria.model.Cuenta;
import co.com.bancolombia.aplicacionbancaria.model.Transaccion;
import co.com.bancolombia.aplicacionbancaria.repository.CuentaRepository;
import co.com.bancolombia.aplicacionbancaria.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransaccionService {

    private final CuentaRepository CUENTAS_DB;

    public TransaccionService(CuentaRepository cuentasDB) {
        this.CUENTAS_DB = cuentasDB;
    }

    public List<Transaccion> consultaTransacciones (CuentaDTO cuentaDTO) {
        Cuenta cuenta = buscarCuenta(cuentaDTO.id());

        return cuenta.transacciones().stream()
                .limit(cuentaDTO.numTransacc())
                .collect(Collectors.toList());
    }

    public Cuenta buscarCuenta(Long idCuenta) {
        Optional<Cuenta> cuentaSelect = CUENTAS_DB.findById(idCuenta);
        if (cuentaSelect.isEmpty())
            throw new NoSuchElementException("No existe una cuenta con el id proporcionado");

        return cuentaSelect.get();
    }
}
