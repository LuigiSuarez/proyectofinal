package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.dto.reponse.OdontologoResponseDto;
import com.digitalhouse.proyectofinal.dto.reponse.PacienteResponseDto;
import com.digitalhouse.proyectofinal.dto.reponse.TurnoModifyDto;
import com.digitalhouse.proyectofinal.dto.reponse.TurnoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.TurnoRequestDto;
import com.digitalhouse.proyectofinal.entity.Odontologo;
import com.digitalhouse.proyectofinal.entity.Paciente;
import com.digitalhouse.proyectofinal.entity.Turno;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.ITurnoRepository;
import com.digitalhouse.proyectofinal.service.IOdontologoService;
import com.digitalhouse.proyectofinal.service.IPacienteService;
import com.digitalhouse.proyectofinal.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurnoService implements ITurnoService {
    private final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    private ITurnoRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;
    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService, ModelMapper modelMapper) {
        this.turnoRepository= turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.modelMapper = modelMapper;
    }
    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto) {

        logger.info("Request de turno: {}", turnoRequestDto);

        Optional<Paciente>paciente=pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo>odontologo=odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());

        if (paciente.isEmpty() || odontologo.isEmpty()) {
            logger.error("Error al guardar turno: Paciente u Odontólogo no encontrados");
            throw new ResourceNotFoundException("Paciente u Odontólogo no encontrados");
        }

        Turno turno = new Turno();
        turno.setPaciente(paciente.get());
        turno.setOdontologo(odontologo.get());
        turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));

        Turno turnoDesdeDB = turnoRepository.save(turno);
        TurnoResponseDto turnoResponseDto = convertirTurnoEnResponse(turnoDesdeDB);

        logger.info("Turno guardado con éxito:Response: {}", turnoResponseDto);

        return turnoResponseDto;
    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        logger.info("Buscando turno con ID: {}", id);
        Optional<Turno> turno = turnoRepository.findById(id);
        if(turno.isPresent()){
            TurnoResponseDto turnoRespuesta = convertirTurnoEnResponse(turno.get());
            return Optional.of(turnoRespuesta);
        } else {
            logger.error("Turno con ID {} no encontrado", id);
            throw new ResourceNotFoundException("El turno no fue encontrado");
        }
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        logger.info("Buscando todos los turnos");
        List<Turno> turnosDesdeBD = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for(Turno t: turnosDesdeBD){
            TurnoResponseDto turnoRespuesta =convertirTurnoEnResponse(t);
            logger.info("Turno encontrado: {}", turnoRespuesta);
            turnosRespuesta.add(turnoRespuesta);
        }
        return turnosRespuesta;
    }

    @Override
    public void modificarTurno(TurnoModifyDto turnoModifyDto) {
        logger.info("Modificando turno con datos: {}", turnoModifyDto);

        Optional<Paciente> paciente = pacienteService.buscarPorId(turnoModifyDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turnoModifyDto.getOdontologo_id());

        if (paciente.isEmpty() || odontologo.isEmpty()) {
            logger.error("Error al modificar turno: Paciente u Odontólogo no encontrados");
            throw new ResourceNotFoundException("Paciente u Odontólogo no encontrados");
        }

        Turno turno = new Turno(turnoModifyDto.getId(), paciente.get(), odontologo.get(), LocalDate.parse(turnoModifyDto.getFecha()));
        turnoRepository.save(turno);
        logger.info("Turno modificado con éxito: {}", turno);

    }

    @Override
    public void eliminarTurno(Integer id) {
        logger.info("Intentando eliminar turno con ID: {}", id);

        Optional<TurnoResponseDto> turnoEncontrado = buscarPorId(id);

        if (turnoEncontrado.isEmpty()) {
            logger.error("Turno con ID {} no encontrado para eliminar", id);
            throw new ResourceNotFoundException("Turno con ID " + id + " no encontrado.");
        }

        turnoRepository.deleteById(id);
        logger.info("Turno eliminado con éxito: ID {}", id);
    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnosPorPaciente(String pacienteApellido) {
        logger.info("Buscando turnos para el paciente con apellido: {}", pacienteApellido);

        Optional<Turno> turno = turnoRepository.buscarPorApellidoPaciente(pacienteApellido);

        if (turno.isPresent()) {
            TurnoResponseDto turnoParaResponder = convertirTurnoEnResponse(turno.get());
            return Optional.of(turnoParaResponder);
        } else {
            logger.warn("No se encontraron turnos para el paciente con apellido: {}", pacienteApellido);
            return Optional.empty();
        }
    }

    @Override
    public List<TurnoResponseDto> buscarTurnosPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        Optional<Turno> turnos = turnoRepository.buscarTurnosPorRangoDeFechas(fechaInicio, fechaFin);
        List<TurnoResponseDto> turnosRespuesta = turnos.stream()
                .map(this::convertirTurnoEnResponse)
                .collect(Collectors.toList());
        return turnosRespuesta;
    }

    @Override
    public Optional<TurnoResponseDto> buscarTurnosPorApellidoOdontologo(String odontologoApellido) {
        logger.info("Buscando turnos para el odontologo con apellido: {}", odontologoApellido);
        Optional<Turno> turnos = turnoRepository.buscarTurnosPorApellidoOdontologo(odontologoApellido);

        TurnoResponseDto turnosRespuesta =convertirTurnoEnResponse(turnos.get());
        return Optional.of(turnosRespuesta);
    }

    private TurnoResponseDto convertirTurnoEnResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        logger.info("TurnoResponseDto generado: {}", turnoResponseDto);
        return turnoResponseDto;
    }
}

