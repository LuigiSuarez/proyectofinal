package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.dto.request.PacienteRequestDto;
import com.digitalhouse.proyectofinal.entity.Paciente;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface IPacienteService {
    Paciente guardarPaciente(PacienteRequestDto paciente);

    Optional<Paciente> buscarPorId(Integer id);
    List<Paciente> buscarTodos();
    void modificarPaciente(Paciente paciente);

    void eliminarPaciente(Integer id);

    //Metodos de la clase
    List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre);
    List<Paciente> buscarPorUnaParteApellido(String parte);

    //Metodos propuestos
    List<Paciente> buscarPacientesConFechaIngresoPosteriorA(LocalDate fecha);
    long contarPacientesPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);
    long contarPacientesPorProvincia(String provincia);


}
