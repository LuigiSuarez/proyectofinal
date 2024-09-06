package com.digitalhouse.proyectofinal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDto {
    private String apellido;
    private String nombre;
    private String dni;
    private LocalDate fechaIngreso;
    private Integer domicilioId;
}
