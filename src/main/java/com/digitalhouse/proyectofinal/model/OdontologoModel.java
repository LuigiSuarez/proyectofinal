package com.digitalhouse.proyectofinal.model;

public class OdontologoModel {
    private Integer id;
    private int NoMatricula;
    private String Nombre;
    private  String Apellido;

    public OdontologoModel(Integer id, int noMatricula, String nombre, String apellido) {
        this.id = id;
        NoMatricula = noMatricula;
        Nombre = nombre;
        Apellido = apellido;
    }

    public OdontologoModel(int noMatricula, String nombre, String apellido) {
        NoMatricula = noMatricula;
        Nombre = nombre;
        Apellido = apellido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNoMatricula() {
        return NoMatricula;
    }

    public void setNoMatricula(int noMatricula) {
        NoMatricula = noMatricula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    @Override
    public String toString() {
        return "OdontologoModel{" +
                "id=" + id +
                ", NoMatricula=" + NoMatricula +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                '}';
    }
}
