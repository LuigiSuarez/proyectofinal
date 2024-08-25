package com.digitalhouse.proyectofinal.dao;

import java.util.List;

public interface IDao <T>{
    T guardar(T t);
    T buscarPorId(Integer id);
    List<T> listaTodos();
    void modificar(T t);
    void borrar(Integer id);

}
