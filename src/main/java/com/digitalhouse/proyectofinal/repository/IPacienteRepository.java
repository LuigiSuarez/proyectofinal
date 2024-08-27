package com.digitalhouse.proyectofinal.repository;

import com.digitalhouse.proyectofinal.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository  extends JpaRepository<Paciente, Integer> {

}
