package co.bancolombia.aplicacionbancaria.repository;

import co.bancolombia.aplicacionbancaria.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}