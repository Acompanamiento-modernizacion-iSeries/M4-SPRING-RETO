package com.bancolombia.cuentabancaria.repository;

import com.bancolombia.cuentabancaria.model.entity.CuentaBancariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<CuentaBancariaEntity, Long> {

}
