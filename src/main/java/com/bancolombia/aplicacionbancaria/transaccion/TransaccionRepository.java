package com.bancolombia.aplicacionbancaria.transaccion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    @Query(value = "SELECT t FROM Transaccion t WHERE t.cuenta.numeroCuenta = :numeroCuenta ORDER BY t.fecha DESC")
    List<Transaccion> findTop5ByCuentaNumeroCuentaOrderByFechaDesc(@Param("numeroCuenta") String numeroCuenta);
}
