package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.dto.reponse.OdontologoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.OdontologoModifyDto;
import com.digitalhouse.proyectofinal.dto.request.OdontologoRequestDto;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    OdontologoResponseDto guardarOdontologo(OdontologoRequestDto odontologoRequestDto);
    Optional<OdontologoResponseDto> buscarPorId(Integer id);

    List<OdontologoResponseDto> buscarTodos();
    void modificarOdontologo(OdontologoModifyDto odontologoModifyDto);
    void eliminarOdontologo(Integer id);


}
