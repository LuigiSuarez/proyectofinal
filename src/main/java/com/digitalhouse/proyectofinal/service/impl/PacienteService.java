package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.entity.Paciente;
import com.digitalhouse.proyectofinal.exception.BadRequestException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.IPacienteRepository;
import com.digitalhouse.proyectofinal.service.IPacienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        if(paciente == null || paciente.getNombre() == null || paciente.getApellido() ==null){
            throw new BadRequestException("El paciente no puede ser nulo y debe tener un nombre y apellido.");
        }
        return pacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public void modificarPaciente(Paciente paciente) {
        if(paciente == null || paciente.getId()==null){
            throw new BadRequestException("El paciente no puede ser nulo y debe tener un ID.");
        }
        pacienteRepository.save(paciente);
    }

    @Override
    public void eliminarPaciente(Integer id) {
        if(!pacienteRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontró el paciente con el ID " + id);
        }
        pacienteRepository.deleteById(id);
    }

    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        return pacienteRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Paciente> buscarPorUnaParteApellido(String parte) {
        return pacienteRepository.buscarPorParteApellido(parte);
    }
}
