package com.bancolombia.aplicacionbancaria.cuenta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}
