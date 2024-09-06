package com.digitalhouse.proyectofinal.service;

import com.digitalhouse.proyectofinal.dto.request.PacienteRequestDto;
import com.digitalhouse.proyectofinal.entity.Domicilio;
import com.digitalhouse.proyectofinal.entity.Paciente;
import com.digitalhouse.proyectofinal.exception.BadRequestException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.IDomicilioRepository;
import com.digitalhouse.proyectofinal.repository.IPacienteRepository;
import com.digitalhouse.proyectofinal.service.impl.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {
    @Mock
    private IPacienteRepository pacienteRepository;

    @Mock
    private IDomicilioRepository domicilioRepository;
    @InjectMocks
    private PacienteService pacienteService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGuardarPacienteConDtoValido() {
        PacienteRequestDto requestDto = new PacienteRequestDto();
        requestDto.setNombre("Juan");
        requestDto.setApellido("Pérez");
        requestDto.setDni("12345678");
        requestDto.setFechaIngreso(LocalDate.of(2024, 9, 5));
        requestDto.setDomicilioId(1);

        Domicilio domicilio = new Domicilio();
        domicilio.setId(1);

        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setDni("12345678");
        paciente.setFechaIngreso(LocalDate.of(2024, 9, 5));
        paciente.setDomicilio(domicilio);

        when(domicilioRepository.findById(1)).thenReturn(Optional.of(domicilio));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente pacienteGuardado = pacienteService.guardarPaciente(requestDto);

        assertNotNull(pacienteGuardado);
        assertEquals("Juan", pacienteGuardado.getNombre());
        assertEquals("Pérez", pacienteGuardado.getApellido());
        assertEquals("12345678", pacienteGuardado.getDni());
        assertEquals(LocalDate.of(2024, 9, 5), pacienteGuardado.getFechaIngreso());
        assertEquals(domicilio, pacienteGuardado.getDomicilio());
        assertEquals(1, pacienteGuardado.getDomicilio().getId());
        //verify(pacienteRepository).save(paciente);

        verify(pacienteRepository).save(argThat(p ->
                "Juan".equals(p.getNombre()) &&
                        "Pérez".equals(p.getApellido()) &&
                        "12345678".equals(p.getDni()) &&
                        LocalDate.of(2024, 9, 5).equals(p.getFechaIngreso()) &&
                        1 == p.getDomicilio().getId()
        ));

    }

    @Test
    void testGuardarPacienteConDtoNulo() {
        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> pacienteService.guardarPaciente(null)
        );
        assertEquals("El paciente no puede ser nulo y debe tener un nombre y apellido.", thrown.getMessage());
    }

    @Test
    void testGuardarPacienteConDatosInvalidos() {
        PacienteRequestDto requestDto = new PacienteRequestDto();
        requestDto.setNombre("Juan");
        // Apellido es nulo

        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> pacienteService.guardarPaciente(requestDto)
        );
        assertEquals("El paciente no puede ser nulo y debe tener un nombre y apellido.", thrown.getMessage());
    }

    @Test
    void testBuscarPorIdPacienteExistente() {
        Integer id = 1;
        Paciente paciente = new Paciente();
        paciente.setId(id);
        when(pacienteRepository.findById(id)).thenReturn(Optional.of(paciente));

        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(id);

        assertTrue(pacienteEncontrado.isPresent());
        assertEquals(id, pacienteEncontrado.get().getId());
        verify(pacienteRepository).findById(id);
    }

    @Test
    void testBuscarPorIdPacienteNoExistente() {
        Integer id = 1;
        when(pacienteRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(id);

        assertFalse(pacienteEncontrado.isPresent());
        verify(pacienteRepository).findById(id);
    }

    @Test
    void testBuscarTodosPacientes() {
        Paciente paciente1 = new Paciente();
        Paciente paciente2 = new Paciente();
        List<Paciente> pacientes = List.of(paciente1, paciente2);
        when(pacienteRepository.findAll()).thenReturn(pacientes);

        List<Paciente> pacientesEncontrados = pacienteService.buscarTodos();

        assertEquals(2, pacientesEncontrados.size());
        verify(pacienteRepository, times(2)).findAll();
    }

    @Test
    void testModificarPacienteConDatosValidos() {
        Paciente paciente = new Paciente();
        paciente.setId(1);

        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        pacienteService.modificarPaciente(paciente);

        verify(pacienteRepository).save(paciente);
    }

    @Test
    void testModificarPacienteConDatosInvalidos() {
        Paciente paciente = new Paciente();

        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> pacienteService.modificarPaciente(paciente)
        );
        assertEquals("El paciente no puede ser nulo y debe tener un ID.", thrown.getMessage());
    }

    @Test
    void testEliminarPacienteExistente() {
        Integer id = 1;
        when(pacienteRepository.existsById(id)).thenReturn(true);

        pacienteService.eliminarPaciente(id);

        verify(pacienteRepository).deleteById(id);
    }

    @Test
    void testEliminarPacienteNoExistente() {
        Integer id = 1;
        when(pacienteRepository.existsById(id)).thenReturn(false);

        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> pacienteService.eliminarPaciente(id)
        );
        assertEquals("No se encontró el paciente con el ID " + id, thrown.getMessage());
        verify(pacienteRepository, never()).deleteById(id);
    }


    /*
    static final Logger logger = LoggerFactory.getLogger(PacienteServiceTest.class);
    PacienteService pacienteService = new PacienteService(new DaoH2Paciente());
    @BeforeAll
    static void crearTablas(){
        H2Connection.crearTablas();
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
     */

}