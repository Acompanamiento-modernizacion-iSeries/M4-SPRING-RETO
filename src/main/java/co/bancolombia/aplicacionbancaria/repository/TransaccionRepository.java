package co.bancolombia.aplicacionbancaria.repository;

import co.bancolombia.aplicacionbancaria.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    @Query(value = "SELECT t.* FROM transaccion t inner join cuenta b on t.cuenta_id = b.id_cuenta " +
            "where b.nro_cuenta= :nroCuenta ORDER BY fecha DESC, hora DESC LIMIT 5", nativeQuery = true)
    List<Transaccion> findTop5ByNroCuenta(Long nroCuenta);

}
