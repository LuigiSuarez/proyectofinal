package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.dto.reponse.OdontologoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.OdontologoRequestDto;
import com.digitalhouse.proyectofinal.entity.Odontologo;
import com.digitalhouse.proyectofinal.exception.BadRequestException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.IOdontologoRepository;
import com.digitalhouse.proyectofinal.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest

public class OdontologoServiceTest {
    @Mock
    private IOdontologoRepository odontologoRepository;

    @InjectMocks
    private OdontologoService odontologoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarOdontologoConDatosValidos() {
        // Given
        OdontologoRequestDto requestDto = new OdontologoRequestDto();
        requestDto.setNombre("Juan");
        requestDto.setApellido("Perez");
        requestDto.setNoMatricula(123);

        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Juan");
        odontologo.setApellido("Perez");
        odontologo.setNoMatricula(123);

        when(odontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);

        // When
        OdontologoResponseDto responseDto = odontologoService.guardarOdontologo(requestDto);

        // Then
        assertNotNull(responseDto);
        assertEquals("Juan", responseDto.getNombre());
        verify(odontologoRepository, times(1)).save(any(Odontologo.class));
    }

    @Test
    void testGuardarOdontologoConDatosInvalidos() {
        // Given
        OdontologoRequestDto requestDto = new OdontologoRequestDto();

        // When / Then
        assertThrows(BadRequestException.class, () -> odontologoService.guardarOdontologo(requestDto));
        verify(odontologoRepository, never()).save(any(Odontologo.class));
    }

    @Test
    void testBuscarPorIdExistente() {
        // Given
        Odontologo odontologo = new Odontologo();
        odontologo.setId(1);
        odontologo.setNombre("Ana");

        when(odontologoRepository.findById(1)).thenReturn(Optional.of(odontologo));

        // When
        Optional<Odontologo> result = odontologoService.buscarPorId(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Ana", result.get().getNombre());
        verify(odontologoRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorIdNoExistente() {
        // Given
        when(odontologoRepository.findById(1)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ResourceNotFoundException.class, () -> odontologoService.buscarPorId(1));
        verify(odontologoRepository, times(1)).findById(1);
    }

    @Test
    void testEliminarOdontologo() {
        // Given
        Integer id = 1;
        doNothing().when(odontologoRepository).deleteById(id);

        // When
        odontologoService.eliminarOdontologo(id);

        // Then
        verify(odontologoRepository, times(1)).deleteById(id);
    }
}
