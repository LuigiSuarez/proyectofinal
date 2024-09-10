package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.dto.reponse.OdontologoResponseDto;
import com.digitalhouse.proyectofinal.dto.reponse.PacienteResponseDto;
import com.digitalhouse.proyectofinal.dto.reponse.TurnoModifyDto;
import com.digitalhouse.proyectofinal.service.impl.TurnoService;
import com.digitalhouse.proyectofinal.dto.reponse.TurnoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.TurnoRequestDto;
import com.digitalhouse.proyectofinal.entity.Odontologo;
import com.digitalhouse.proyectofinal.entity.Paciente;
import com.digitalhouse.proyectofinal.entity.Turno;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.ITurnoRepository;
import com.digitalhouse.proyectofinal.service.IOdontologoService;
import com.digitalhouse.proyectofinal.service.IPacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TurnoServiceTest {

    @Mock
    private ITurnoRepository turnoRepository;

    @Mock
    private IPacienteService pacienteService;

    @Mock
    private IOdontologoService odontologoService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TurnoService turnoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarTurno_Exito() {
        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(1);
        turnoRequestDto.setOdontologo_id(2);
        turnoRequestDto.setFecha("2024-09-15");

        Paciente paciente = new Paciente();
        paciente.setId(1);
        Odontologo odontologo = new Odontologo();
        odontologo.setId(2);

        when(pacienteService.buscarPorId(1)).thenReturn(Optional.of(paciente));
        when(odontologoService.buscarPorId(2)).thenReturn(Optional.of(odontologo));

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));

        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);
        TurnoResponseDto turnoResponseDto = new TurnoResponseDto();
        when(modelMapper.map(turno, TurnoResponseDto.class)).thenReturn(turnoResponseDto);

        TurnoResponseDto result = turnoService.guardarTurno(turnoRequestDto);

        assertNotNull(result);
        verify(turnoRepository).save(any(Turno.class));
    }

    @Test
    void testGuardarTurno_PacienteNoEncontrado() {
        TurnoRequestDto turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(1);
        turnoRequestDto.setOdontologo_id(2);
        turnoRequestDto.setFecha("2024-09-15");

        when(pacienteService.buscarPorId(1)).thenReturn(Optional.empty());
        when(odontologoService.buscarPorId(2)).thenReturn(Optional.of(new Odontologo()));

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            turnoService.guardarTurno(turnoRequestDto);
        });

        assertEquals("Paciente u Odontólogo no encontrados", thrown.getMessage());
    }

    @Test
    void testBuscarPorId_Exito() {
        Turno turno = new Turno();
        turno.setId(1);
        TurnoResponseDto turnoResponseDto = new TurnoResponseDto();

        when(turnoRepository.findById(1)).thenReturn(Optional.of(turno));
        when(modelMapper.map(turno, TurnoResponseDto.class)).thenReturn(turnoResponseDto);

        Optional<TurnoResponseDto> result = turnoService.buscarPorId(1);

        assertTrue(result.isPresent());
        assertEquals(turnoResponseDto, result.get());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        when(turnoRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            turnoService.buscarPorId(1);
        });

        assertEquals("El turno no fue encontrado", thrown.getMessage());
    }

    @Test
    void testModificarTurno() {
        TurnoModifyDto turnoModifyDto = new TurnoModifyDto();
        turnoModifyDto.setPaciente_id(1);
        turnoModifyDto.setOdontologo_id(1);
        turnoModifyDto.setFecha("2024-09-09");

        Paciente paciente = new Paciente();
        Odontologo odontologo = new Odontologo();

        when(pacienteService.buscarPorId(1)).thenReturn(Optional.of(paciente));
        when(odontologoService.buscarPorId(1)).thenReturn(Optional.of(odontologo));

        turnoService.modificarTurno(turnoModifyDto);

        verify(turnoRepository).save(argThat(turno ->
                turno.getPaciente().equals(paciente) &&
                        turno.getOdontologo().equals(odontologo) &&
                        turno.getFecha().equals(LocalDate.parse("2024-09-09"))
        ));
    }


    @Test
    void testBuscarTurnosPorPacienteNoEncontrado() {
        String pacienteApellido = "Perez";

        when(turnoRepository.buscarPorApellidoPaciente(pacienteApellido)).thenReturn(Optional.empty());

        Optional<TurnoResponseDto> result = turnoService.buscarTurnosPorPaciente(pacienteApellido);

        assertEquals(Optional.empty(), result);
    }

    @Test
    void testEliminarTurno() {
        Integer id = 1;
        when(turnoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            turnoService.eliminarTurno(id);
        });
        verify(turnoRepository, never()).deleteById(id);
    }



}
