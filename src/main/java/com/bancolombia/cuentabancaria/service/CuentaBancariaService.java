package com.bancolombia.cuentabancaria.service;

import com.bancolombia.cuentabancaria.model.entity.CuentaBancariaEntity;
import com.bancolombia.cuentabancaria.model.entity.TransaccionEntity;
import com.bancolombia.cuentabancaria.model.request.TransaccionRQ;
import com.bancolombia.cuentabancaria.repository.CuentaRepository;
import com.bancolombia.cuentabancaria.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class CuentaBancariaService {

    private final CuentaRepository repository;
    private final TransaccionRepository transaccionRepository;

    public CuentaBancariaService(CuentaRepository repository, TransaccionRepository transaccionRepository) {
        this.repository = repository;
        this.transaccionRepository = transaccionRepository;
    }

    public boolean validSaldo(BigDecimal valor){
        if(valor.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
        return true;
    }

    public CuentaBancariaEntity getCuenta(Long id){
        Optional<CuentaBancariaEntity> cuenta = repository.findById(id);
        if(cuenta.isEmpty()){
            throw new NullPointerException("La Cuenta bancaria no existe");
        }
        CuentaBancariaEntity cuentaEntity = cuenta.get();
        return cuentaEntity;
    }

    public CuentaBancariaEntity depositoSucursal(TransaccionRQ transaccionRQ){
        CuentaBancariaEntity cuentaBancaria = getCuenta(transaccionRQ.getIdCuenta());
        if(validSaldo(transaccionRQ.getValor())){
            cuentaBancaria.depositoSucursal(transaccionRQ.getValor());
            updateCuentaBancaria(cuentaBancaria, "deposito", transaccionRQ.getValor());
        }
        return cuentaBancaria;
    }

    public CuentaBancariaEntity depositoCajero(TransaccionRQ transaccionRQ){
        CuentaBancariaEntity cuentaBancaria = getCuenta(transaccionRQ.getIdCuenta());
        if(validSaldo(transaccionRQ.getValor())){
            cuentaBancaria.depositoCajero(transaccionRQ.getValor());
            updateCuentaBancaria(cuentaBancaria, "deposito", transaccionRQ.getValor());
        }
        return cuentaBancaria;
    }

    public CuentaBancariaEntity depositoCuenta(TransaccionRQ transaccionRQ){
        CuentaBancariaEntity cuentaBancaria = getCuenta(transaccionRQ.getIdCuenta());
        if(validSaldo(transaccionRQ.getValor())){
            cuentaBancaria.depositoCuenta(transaccionRQ.getValor());
            updateCuentaBancaria(cuentaBancaria, "deposito", transaccionRQ.getValor());
        }
        return cuentaBancaria;
    }

    public CuentaBancariaEntity compraFisico(TransaccionRQ transaccionRQ){
        CuentaBancariaEntity cuentaEntity = getCuenta(transaccionRQ.getIdCuenta());
        if(validSaldo(transaccionRQ.getValor())){
            cuentaEntity.compraFisico(transaccionRQ.getValor());
            if (cuentaEntity.getSaldo().compareTo(BigDecimal.ZERO) < 0){
                throw new IllegalArgumentException("Saldo insuficiente para realizar la compra");
            }
            updateCuentaBancaria(cuentaEntity, "retiro", transaccionRQ.getValor());
        }
        return cuentaEntity;
    }

    public CuentaBancariaEntity compraWeb(TransaccionRQ transaccionRQ){
        CuentaBancariaEntity cuentaEntity = getCuenta(transaccionRQ.getIdCuenta());
        if(validSaldo(transaccionRQ.getValor())){
            cuentaEntity.compraWeb(transaccionRQ.getValor());
            if (cuentaEntity.getSaldo().compareTo(BigDecimal.ZERO) < 0){
                throw new IllegalArgumentException("Saldo insuficiente para realizar la compra");
            }
            updateCuentaBancaria(cuentaEntity, "retiro", transaccionRQ.getValor());
        }
        return cuentaEntity;
    }

    public CuentaBancariaEntity retiroCajero(TransaccionRQ transaccionRQ){
        CuentaBancariaEntity cuentaEntity = getCuenta(transaccionRQ.getIdCuenta());
        if(validSaldo(transaccionRQ.getValor())){
            cuentaEntity.retiroCajero(transaccionRQ.getValor());
            if (cuentaEntity.getSaldo().compareTo(BigDecimal.ZERO) < 0){
                throw new IllegalArgumentException("Saldo insuficiente para realizar el retiro");
            }
            updateCuentaBancaria(cuentaEntity, "retiro", transaccionRQ.getValor());
        }
        return cuentaEntity;
    }

    public CuentaBancariaEntity updateCuentaBancaria(CuentaBancariaEntity cuentaBancariaEntity, String tipoTransaccion,
                                                     BigDecimal valor){
        TransaccionEntity transaccion = new TransaccionEntity();
        transaccion.setCuentabancaria(cuentaBancariaEntity);
        transaccion.setTipotransaccion(tipoTransaccion);
        transaccion.setMonto(valor);
        transaccion.setFecharegistro(new Date());
        transaccionRepository.save(transaccion);
        return repository.save(cuentaBancariaEntity);
    }
}
