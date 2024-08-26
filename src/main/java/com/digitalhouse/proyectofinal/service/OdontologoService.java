package com.digitalhouse.proyectofinal.service;


import com.digitalhouse.proyectofinal.dao.IDao;
import com.digitalhouse.proyectofinal.model.OdontologoModel;

import java.util.List;

public class OdontologoService {
    private IDao<OdontologoModel> OdontologoIDao;

    public OdontologoService(IDao<OdontologoModel> odontologoIDao) {
        this.OdontologoIDao=odontologoIDao;
    }

    public OdontologoModel guardar(OdontologoModel odontologo){
        return OdontologoIDao.guardar(odontologo);
    }
    public OdontologoModel buscarPorId(Integer id){
        return OdontologoIDao.buscarPorId(id);
    }
    public List<OdontologoModel> listar(){
        return OdontologoIDao.listaTodos();
    }

    public void modificarOdontologo(OdontologoModel odontologo){OdontologoIDao.modificar(odontologo);
    }

}
