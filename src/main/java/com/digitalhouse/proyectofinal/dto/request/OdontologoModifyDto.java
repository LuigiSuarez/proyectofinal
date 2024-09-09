package com.digitalhouse.proyectofinal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OdontologoModifyDto {
    private Integer id;
    private String matricula;
    private String nombre;
    private String apellido;
}
