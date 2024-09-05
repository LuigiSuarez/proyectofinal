package com.digitalhouse.proyectofinal.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnoResponseDto {
    Integer id;
    PacienteResponseDto pacienteResponseDto;
    OdontologoResponseDto odontologoResponseDto;
    private String fecha;
}
