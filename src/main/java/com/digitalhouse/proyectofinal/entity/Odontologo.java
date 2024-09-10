package com.digitalhouse.proyectofinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int noMatricula;
    private String nombre;
    private  String apellido;

    @Override
    public String toString() {
        return "OdontologoModel{" +
                "id=" + id +
                ", NoMatricula=" + noMatricula +
                ", Nombre='" + nombre + '\'' +
                ", Apellido='" + apellido + '\'' +
                '}';
    }
}
