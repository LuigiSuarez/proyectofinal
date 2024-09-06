package com.digitalhouse.proyectofinal.repository;

import com.digitalhouse.proyectofinal.entity.Paciente;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPacienteRepository  extends JpaRepository<Paciente, Integer> {

    List<Paciente> findByApellidoAndNombre(String apellido, String nombre);

    @Query("Select p from Paciente p where LOWER(p.apellido) LIKE LOWER(CONCAT('%',:parteApellido,'%'))")
    List<Paciente> buscarPorParteApellido(String parteApellido);

    @Query("Select p from Paciente p where p.fechaIngreso > :fecha")
    List<Paciente> buscarPacientesConFechaIngresoPosteriorA(LocalDate fecha);

    @Query("Select count(p) from Paciente p where p.fechaIngreso between :fechaInicio and :fechaFin")
    long contarPacientesPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("Select count(p) from Paciente p where p.domicilio.provincia = :provincia")
    long contarPacientesPorProvincia(String provincia);

}
