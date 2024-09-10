package com.digitalhouse.proyectofinal.repository;

import com.digitalhouse.proyectofinal.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno,Integer> {

    @Query("Select t from Turno t join t.paciente p with p.apellido = :pacienteApellido ")
    Optional<Turno> buscarPorApellidoPaciente(String pacienteApellido);

    @Query("Select t from Turno t where t.fecha between :fechaInicio AND :fechaFin")
    Optional<Turno> buscarTurnosPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin);

    @Query("Select t from Turno t join t.odontologo o where o.Apellido = :odontologoApellido ")
    Optional<Turno> buscarTurnosPorApellidoOdontologo(String odontologoApellido);
}
