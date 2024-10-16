package com.bancolombia.sistemabancario.sistemabancario.services;

import com.bancolombia.sistemabancario.sistemabancario.models.Cuenta;
import com.bancolombia.sistemabancario.sistemabancario.models.DTO.ConsultaPorIdCuentaDTO;
import com.bancolombia.sistemabancario.sistemabancario.models.Transaccion;
import com.bancolombia.sistemabancario.sistemabancario.repository.CuentaRepository;
import com.bancolombia.sistemabancario.sistemabancario.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ConsultaCuentaService {
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository HistoricoTransaccion;

    public ConsultaCuentaService(CuentaRepository cuentaRepository, TransaccionRepository historicoTransaccion) {
        this.cuentaRepository = cuentaRepository;
        this.HistoricoTransaccion = historicoTransaccion;
    }

    public Cuenta ConsultaCuenta(ConsultaPorIdCuentaDTO cuentaPorId){
        System.out.println(cuentaPorId.getIdCuenta());
        Cuenta OPtionaldatosCuenta = cuentaRepository.findById(cuentaPorId.getIdCuenta()).orElseThrow(() ->
                new NoSuchElementException("Cuenta no encontrada"));

        Cuenta datosCuenta = OPtionaldatosCuenta;
        return datosCuenta;
    }
    public List<Transaccion> consultaHistoriaTransacciones(ConsultaPorIdCuentaDTO cuentaPorId){
        Transaccion transacciones = new Transaccion();
        return  HistoricoTransaccion.findTransactions(cuentaPorId.getIdCuenta());

    }
}
