package co.bancolombia.aplicacionbancaria.repository;

import co.bancolombia.aplicacionbancaria.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    @Query("SELECT t FROM Transaccion t WHERE t.cuentaAsociada.nroCuenta = :nroCuenta ORDER BY t.fecha DESC, t.hora DESC LIMIT 5")
    List<Transaccion> findTop5ByNroCuenta(@Param("nroCuenta") Long nroCuenta);
}
