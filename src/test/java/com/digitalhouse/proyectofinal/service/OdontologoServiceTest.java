package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.dao.impl.DaoH2Odontologo;
import com.digitalhouse.proyectofinal.model.OdontologoModel;
import com.digitalhouse.proyectofinal.db.H2Connection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OdontologoServiceTest {
    static final Logger logger = LoggerFactory.getLogger(OdontologoServiceTest.class);
    OdontologoService odontologoService = new OdontologoService(new DaoH2Odontologo());

    @BeforeAll
    static void crearTablas(){
        H2Connection.crearTablas();
    }

    @Test
    @DisplayName("Testear guardar y obtener la lista de odontologos")
    void testGuardarYListarOdontologos(){

        OdontologoModel odontologo = new OdontologoModel(300,"Lina", "Suarez");
        OdontologoModel odontologo2 = new OdontologoModel(301,"Juan", "Suarez");
        OdontologoModel odontologo3 = new OdontologoModel(302,"Luis", "Suarez");

        odontologoService.guardar(odontologo);
        odontologoService.guardar(odontologo2);
        odontologoService.guardar(odontologo3);


        List<OdontologoModel> odontologos = odontologoService.listar();

        assertEquals(3, odontologos.size());

    }

    @Test
    @DisplayName("Testear que un odontologo pueda acceder por id")
    void testAccederPorId(){
        OdontologoModel odontologo = new OdontologoModel(1,300,"Lina", "Suarez");
        odontologoService.guardar(odontologo);

        OdontologoModel odontologoDesdeDb = odontologoService.buscarPorId(1);
        assertEquals(1, odontologoDesdeDb.getId());
    }

    @Test
    @DisplayName("Testear modificar un odontologo")
    void testModificarOdontologo() {
        OdontologoModel odontologoOriginal = new OdontologoModel(1,300, "Lina", "Suarez");
        odontologoService.guardar(odontologoOriginal);

        OdontologoModel odontologoModificado = new OdontologoModel(1,300, "Lina", "Gomez");
        odontologoService.modificarOdontologo(odontologoModificado);

        List<OdontologoModel> odontologos = odontologoService.listar();

        OdontologoModel odontologoEncontrado = odontologos.stream()
                .filter(o -> o.getId() == odontologoModificado.getId())
                .findFirst()
                .orElse(null);

        assertNotNull(odontologoEncontrado, "Odontologo no encontrado");
        assertEquals("Gomez", odontologoEncontrado.getApellido(), "El apellido no fue modificado correctamente");
        assertEquals("Lina", odontologoEncontrado.getNombre(), "El nombre no debería haber cambiado");
        assertEquals(300, odontologoEncontrado.getNoMatricula(), "El número de matrícula no debería haber cambiado");
    }

}
