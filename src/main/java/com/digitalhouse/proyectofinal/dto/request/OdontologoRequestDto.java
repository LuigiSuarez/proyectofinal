package com.digitalhouse.proyectofinal.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OdontologoRequestDto {
    private int NoMatricula;
    private String Nombre;
    private  String Apellido;
}
