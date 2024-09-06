package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.entity.Paciente;
import com.digitalhouse.proyectofinal.exception.BadRequestException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.IPacienteRepository;
import com.digitalhouse.proyectofinal.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);
    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        logger.info("Intentando guardar un paciente: {}",paciente);
        if(paciente == null || paciente.getNombre() == null || paciente.getApellido() ==null){
            logger.error("Error al guardar el paciente: datos inválidos.");
            throw new BadRequestException("El paciente no puede ser nulo y debe tener un nombre y apellido.");
        }
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
}
