package co.com.bancolombia.aplicacionbancaria.repository;

import co.com.bancolombia.aplicacionbancaria.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

}
