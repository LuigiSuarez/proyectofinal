package com.digitalhouse.proyectofinal.dao.impl;


import com.digitalhouse.proyectofinal.dao.IDao;
import com.digitalhouse.proyectofinal.db.H2Connection;
import com.digitalhouse.proyectofinal.model.OdontologoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoH2Odontologo implements IDao<OdontologoModel> {

    public static final Logger logger = LoggerFactory.getLogger(DaoH2Odontologo.class);
    public static final String INSERT = "INSERT INTO ODONTOLOGOS VALUES(DEFAULT,?,?,? )";
    public static final String SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    public static final String UPDATE = "UPDATE ODONTOLOGO SET MATRICULA=?,NOMBRE=?,APELLIDO=? WHERE ID=?";

    public static final String DELETE =" DELETE FROM ODONTOLOGOS WHERE ID=?";


    @Override
    public OdontologoModel guardar(OdontologoModel odontologo) {
        Connection      connection          = null;
        OdontologoModel odontologoARetornar = null;

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,odontologo.getNoMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.executeUpdate();
            connection.commit();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                odontologoARetornar = new OdontologoModel(id, odontologo.getNoMatricula(), odontologo.getNombre(),
                        odontologo.getApellido());
            }
            logger.info("Odontologo "+ odontologoARetornar);

        }catch (Exception e){
            if(connection != null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error(e.getMessage());
                } finally {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologoARetornar;
    }

    @Override
    public OdontologoModel buscarPorId(Integer id) {
        return null;
    }

    @Override
    public List<OdontologoModel> listaTodos() {
        List<OdontologoModel> odontologos = new ArrayList<>();
        Connection connection = null;

        try{
            connection = H2Connection.getConnection();
            //connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL, Statement.RETURN_GENERATED_KEYS);
            //connection.commit();

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Integer idBD = resultSet.getInt(1);
                Integer idMatricula = resultSet.getInt(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);

                OdontologoModel odontologoAuxiliar =new OdontologoModel(idBD,idMatricula, nombre, apellido);

                odontologos.add(odontologoAuxiliar);

            }
            logger.info("Cantidad de odontologos listados: "+ odontologos.size());

        }catch (Exception e){
            if(connection != null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error(e.getMessage());
                } finally {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return odontologos;
    }

    @Override
    public void modificar(OdontologoModel odontologoModel) {
        Connection connection = null;
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,odontologoModel.getNombre());
            preparedStatement.setString(2, odontologoModel.getApellido());
            preparedStatement.setInt(3, odontologoModel.getNoMatricula());
            preparedStatement.setInt(4, odontologoModel.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            logger.info("Odontologo Modificado"+ odontologoModel);

        }catch (Exception e){
            if(connection != null){
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    logger.error(e.getMessage());
                } finally {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void eliminar(Integer id) {
        Connection      connection = null;
        OdontologoModel odontologo = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            odontologo = buscarPorId(id);
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            logger.info("Paciente eliminado con Exito" + id);

        } catch (Exception e) {
            if(connection != null){
                try{
                   connection.rollback();
                }catch(SQLException ex){
                    logger.error(e.getMessage());
                }finally {
                    try{
                       connection.setAutoCommit(true);
                    }catch(SQLException ex){
                        throw new RuntimeException();
                    }
                }
            }
            logger.error(e.getMessage());
            e.printStackTrace();
        }finally {
            try{
               connection.close();
            }catch(SQLException ex){
                logger.error(ex.getMessage());
                ex.printStackTrace();
            }
        }

        }


}
