package com.digitalhouse.proyectofinal.service;


import com.digitalhouse.proyectofinal.dao.IDao;
import com.digitalhouse.proyectofinal.entity.Odontologo;

import java.util.List;

public class OdontologoService {
    private IDao<Odontologo> OdontologoIDao;

    public OdontologoService(IDao<Odontologo> odontologoIDao) {
        this.OdontologoIDao=odontologoIDao;
    }

    public Odontologo guardar(Odontologo odontologo){
        return OdontologoIDao.guardar(odontologo);
    }
    public Odontologo buscarPorId(Integer id){
        return OdontologoIDao.buscarPorId(id);
    }
    public List<Odontologo> listar(){
        return OdontologoIDao.listaTodos();
    }

    public void modificarOdontologo(Odontologo odontologo){OdontologoIDao.modificar(odontologo);
    }

}
