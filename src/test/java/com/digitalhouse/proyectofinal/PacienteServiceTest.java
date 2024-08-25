package com.digitalhouse.proyectofinal;

import com.digitalhouse.proyectofinal.dao.impl.DaoH2Paciente;
import com.digitalhouse.proyectofinal.model.Domicilio;
import com.digitalhouse.proyectofinal.model.Paciente;
import com.digitalhouse.proyectofinal.service.PacienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PacienteServiceTest {
    static final Logger logger = LoggerFactory.getLogger(PacienteServiceTest.class);
    PacienteService pacienteService = new PacienteService(new DaoH2Paciente());
    @BeforeAll
    static void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:./maven;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }
    @Test
    @DisplayName("Testear que un paciente fue cargado correctamente con su domicilio")
    void caso1(){
        //Dado
        Paciente paciente = new Paciente("Castro", "Maria", "48974646", LocalDate.of(2024, 7, 15),
                                         new Domicilio("Falsa", 145, "CABA", "Buenos Aires"));
        //cuando
        Paciente pacienteDesdeDb = pacienteService.guardarPaciente(paciente);
        // entonces
        assertNotNull(pacienteDesdeDb.getId());
    }

    @Test
    @DisplayName("Testear que un paciente pueda acceder por id")
    void caso2(){
        //Dado
        Integer id = 1;
        //cuando
        Paciente pacienteDesdeDb = pacienteService.buscarPorId(id);
        // entonces
        assertEquals(id, pacienteDesdeDb.getId());
    }

    @Test
    @DisplayName("Listar todos los pacientes")
    void caso3(){
        //Dado
        List<Paciente> pacientes;
        // cuando
        pacientes = pacienteService.buscarTodos();
        // entonces
        assertFalse(pacientes.isEmpty());
    }

    @Test
    @DisplayName("Borar un paciente")
    void testBorrarUnPaciente(){

        Paciente paciente = new Paciente("Gonzalez", "Juan", "12345678", LocalDate.of(2024, 8, 20),
                new Domicilio("Falsa", 123, "CABA", "Buenos Aires"));
        Paciente pacienteGuardado = pacienteService.guardarPaciente(paciente);
        Integer id = pacienteGuardado.getId();

        pacienteService.borrarPaciente(id);

        Paciente pacienteEliminado = pacienteService.buscarPorId(id);
        assertNull(pacienteEliminado, "El paciente debería haber sido eliminado");

    }

}