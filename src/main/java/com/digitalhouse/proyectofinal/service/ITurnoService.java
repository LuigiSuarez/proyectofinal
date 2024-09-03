package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.dto.reponse.TurnoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.TurnoRequestDto;
import com.digitalhouse.proyectofinal.entity.Turno;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    TurnoResponseDto guardarTurno(TurnoRequestDto turno);

    Optional<Turno> buscarPorId(Integer id);
    List<Turno> buscarTodos();
    void modificarTurno(TurnoRequestDto turno);

    void eliminarTurno(Integer id);
}
