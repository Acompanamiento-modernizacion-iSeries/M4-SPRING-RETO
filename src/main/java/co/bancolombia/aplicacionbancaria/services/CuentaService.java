package co.bancolombia.aplicacionbancaria.services;

import co.bancolombia.aplicacionbancaria.models.Cuenta;
import co.bancolombia.aplicacionbancaria.models.Transaccion;
import co.bancolombia.aplicacionbancaria.models.dto.BuscarIdCuentaDTO;
import co.bancolombia.aplicacionbancaria.repository.CuentaRepository;
import co.bancolombia.aplicacionbancaria.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository HistoricoTransaccion;

    public CuentaService(CuentaRepository cuentaRepository, TransaccionRepository historicoTransaccion) {
        this.cuentaRepository = cuentaRepository;
        this.HistoricoTransaccion = historicoTransaccion;
    }

    public Cuenta ConsultaCuenta(BuscarIdCuentaDTO cuentaPorId){
        Cuenta OPtionaldatosCuenta = cuentaRepository.findById(cuentaPorId.getIdCuenta()).orElseThrow(() ->
                new NoSuchElementException("Cuenta no encontrada"));

        Cuenta datosCuenta = OPtionaldatosCuenta;
        return datosCuenta;
    }
    public List<Transaccion> consultaHistoriaTransacciones(BuscarIdCuentaDTO cuentaPorId){
        Transaccion transacciones = new Transaccion();
        return  HistoricoTransaccion.findTransactions(cuentaPorId.getIdCuenta());

    }
}