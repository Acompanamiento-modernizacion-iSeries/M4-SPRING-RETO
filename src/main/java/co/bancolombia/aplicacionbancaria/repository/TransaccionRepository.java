package co.bancolombia.aplicacionbancaria.repository;

import co.bancolombia.aplicacionbancaria.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    @Query("select p from Transaccion p join fetch p.cuenta where p.cuenta.CuentaId =?1 ")
    List<Transaccion> findTransactions(Long cuentaId);

}