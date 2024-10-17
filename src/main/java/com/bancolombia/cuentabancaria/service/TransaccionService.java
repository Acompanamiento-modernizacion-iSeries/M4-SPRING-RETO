package com.bancolombia.cuentabancaria.service;

import com.bancolombia.cuentabancaria.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransaccionService {
    private final TransaccionRepository repository;

    public TransaccionService(TransaccionRepository repository) {
        this.repository = repository;
    }

    public List<Map<String,Object>> historicoTransaccion(Long id){
        List<Map<String,Object>> historicoTransaccion = repository.historicoTransaccion(id);
        if(historicoTransaccion == null || historicoTransaccion.isEmpty()){
            throw new NullPointerException("No existen registros en el historico de transacciones");
        }
        return historicoTransaccion;
    }
}
