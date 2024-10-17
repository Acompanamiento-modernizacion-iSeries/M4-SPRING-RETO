package com.bancolombia.cuentabancaria.repository;

import com.bancolombia.cuentabancaria.model.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TransaccionRepository extends JpaRepository<TransaccionEntity, Long> {

    @Query(value = "SELECT id, idcuenta, tipotransaccion, monto, fecharegistro " +
            "FROM public.transaccion WHERE idcuenta = :id " +
            "ORDER BY fecharegistro DESC LIMIT 5", nativeQuery = true)
    List<Map<String,Object>> historicoTransaccion(@Param("id") Long id);
}
