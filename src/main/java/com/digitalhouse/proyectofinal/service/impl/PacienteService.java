package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.dto.request.PacienteRequestDto;
import com.digitalhouse.proyectofinal.entity.Domicilio;
import com.digitalhouse.proyectofinal.entity.Paciente;
import com.digitalhouse.proyectofinal.exception.BadRequestException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.IDomicilioRepository;
import com.digitalhouse.proyectofinal.repository.IPacienteRepository;
import com.digitalhouse.proyectofinal.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);
    private IPacienteRepository pacienteRepository;
    private IDomicilioRepository domicilioRepository;

    @Autowired
    public PacienteService(IPacienteRepository pacienteRepository, IDomicilioRepository domicilioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.domicilioRepository = domicilioRepository;
    }

    @Override
    public Paciente guardarPaciente(PacienteRequestDto pacienteRequestDto) {
        logger.info("Intentando guardar un paciente: {}",pacienteRequestDto);
        if(pacienteRequestDto == null || pacienteRequestDto.getNombre() == null || pacienteRequestDto.getApellido() ==null){
            logger.error("Error al guardar el paciente: datos inválidos.");
            throw new BadRequestException("El paciente no puede ser nulo y debe tener un nombre y apellido.");
        }

        Paciente paciente = convertirDtoEnPaciente(pacienteRequestDto);
        Paciente savedPaciente = pacienteRepository.save(paciente);
        logger.info("Paciente guardado con éxito: {}", savedPaciente);
        return savedPaciente;
    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        logger.info("Buscando paciente por ID: {}", id);
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (paciente.isPresent()) {
            logger.info("Paciente encontrado: {}", paciente.get());
        } else {
            logger.warn("No se encontró ningún paciente con el ID: {}", id);
        }
        return paciente;
        //return pacienteRepository.findById(id);
    }

    @Override
    public List<Paciente> buscarTodos() {
        logger.info("Número total de pacientes encontrados: {}", pacienteRepository.findAll().size());
        return pacienteRepository.findAll();
    }

    @Override
    public void modificarPaciente(Paciente paciente) {
        logger.info("Modificando paciente: {}", paciente);
        if(paciente == null || paciente.getId()==null){
            logger.error("Error al modificar el paciente: datos inválidos.");
            throw new BadRequestException("El paciente no puede ser nulo y debe tener un ID.");
        }
        pacienteRepository.save(paciente);
        logger.info("Paciente modificado con éxito: {}", paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) {
        logger.info("Intentando eliminar paciente con ID: {}", id);
        if(!pacienteRepository.existsById(id)){
            logger.error("Error al eliminar: no se encontró el paciente con el ID {}", id);
            throw new ResourceNotFoundException("No se encontró el paciente con el ID " + id);
        }
        pacienteRepository.deleteById(id);
        logger.info("Paciente con ID {} eliminado con éxito.", id);
    }

    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        logger.info("Buscando pacientes por apellido '{}' y nombre '{}'.", apellido, nombre);

        List<Paciente> pacientes = pacienteRepository.findByApellidoAndNombre(apellido, nombre);
        logger.info("Número de pacientes encontrados: {}", pacientes.size());

        return pacientes;
    }

    @Override
    public List<Paciente> buscarPorUnaParteApellido(String parte) {
        logger.info("Buscando pacientes por parte del apellido: '{}'.", parte);
        List<Paciente> pacientes = pacienteRepository.buscarPorParteApellido(parte);
        logger.info("Número de pacientes encontrados: {}", pacientes.size());
        return pacientes;
    }

    @Override
    public List<Paciente> buscarPacientesConFechaIngresoPosteriorA(LocalDate fecha) {
        logger.info("Buscando pacientes con fecha de ingreso posterior a: {}", fecha);
        List<Paciente> pacientes = pacienteRepository.buscarPacientesConFechaIngresoPosteriorA(fecha);
        logger.info("Número de pacientes encontrados: {}", pacientes.size());

        return pacientes;
    }
    @Override
    public long contarPacientesPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        logger.info("Contando pacientes ingresados entre {} y {}", fechaInicio, fechaFin);
        long count = pacienteRepository.contarPacientesPorRangoFechas(fechaInicio, fechaFin);
        logger.info("Número de pacientes ingresados en el rango de fechas: {}", count);
        return count;
    }

    @Override
    public long contarPacientesPorProvincia(String provincia) {
        long count = pacienteRepository.contarPacientesPorProvincia(provincia);
        logger.info("Número de pacientes en la provincia {}: {}", provincia, count);

        return count;
    }

    private Paciente convertirDtoEnPaciente(PacienteRequestDto pacienteRequestDto) {
        Paciente paciente = new Paciente();
        paciente.setApellido(pacienteRequestDto.getApellido());
        paciente.setNombre(pacienteRequestDto.getNombre());
        paciente.setDni(pacienteRequestDto.getDni());
        paciente.setFechaIngreso(pacienteRequestDto.getFechaIngreso());

        if (pacienteRequestDto.getDomicilioId() != null) {
            Optional<Domicilio> domicilio = domicilioRepository.findById(pacienteRequestDto.getDomicilioId());
            domicilio.ifPresent(paciente::setDomicilio);
        }
        return paciente;
    }

}
