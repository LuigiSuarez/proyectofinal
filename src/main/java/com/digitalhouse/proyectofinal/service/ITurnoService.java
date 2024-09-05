package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.dto.reponse.TurnoModifyDto;
import com.digitalhouse.proyectofinal.dto.reponse.TurnoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.TurnoRequestDto;
import com.digitalhouse.proyectofinal.entity.Turno;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    TurnoResponseDto guardarTurno(TurnoRequestDto turno);

    Optional<TurnoResponseDto> buscarPorId(Integer id);
    List<TurnoResponseDto> buscarTodos();
    void modificarTurno(TurnoModifyDto turno);

    void eliminarTurno(Integer id);

    Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido);
}
