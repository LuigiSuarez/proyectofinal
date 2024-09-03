package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.dto.reponse.OdontologoResponseDto;
import com.digitalhouse.proyectofinal.dto.reponse.PacienteResponseDto;
import com.digitalhouse.proyectofinal.dto.reponse.TurnoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.TurnoRequestDto;
import com.digitalhouse.proyectofinal.entity.Odontologo;
import com.digitalhouse.proyectofinal.entity.Paciente;
import com.digitalhouse.proyectofinal.entity.Turno;
import com.digitalhouse.proyectofinal.repository.ITurnoRepository;
import com.digitalhouse.proyectofinal.service.IOdontologoService;
import com.digitalhouse.proyectofinal.service.IPacienteService;
import com.digitalhouse.proyectofinal.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private ITurnoService turnoService;
    private ITurnoRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;
    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoService turnoService, IPacienteService pacienteService, IOdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }
    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto) {
        Optional<Paciente>paciente=pacienteService.buscarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo>odontologo=odontologoService.buscarPorId(turnoRequestDto.getOdontologo_id());
        Turno turno = new Turno();
        Turno turnoDesdeDB = null;
        TurnoResponseDto turnoResponseDto = null;
        if (paciente.isPresent() && odontologo.isPresent()){
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));

            turnoDesdeDB = turnoRepository.save(turno);

            turnoResponseDto = convertirTurnoEnResponse(turnoDesdeDB);
        }
        return turnoResponseDto;
    }

    @Override
    public Optional<Turno> buscarPorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Turno> buscarTodos() {
        return null;
    }

    @Override
    public void modificarTurno(TurnoRequestDto turno) {

    }

    @Override
    public void eliminarTurno(Integer id) {
    }

    private TurnoResponseDto convertirTurnoEnResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        return turnoResponseDto;
    }
}

//https://docs.google.com/document/d/19wPYTApRSr_0n5S-f7xzUTRImTg7xCVS1mAtErL8KEk/edit#heading=h.x36oy5demzwb
//https://docs.google.com/document/d/1GUaFZIdh2BYsbdQFKi5BWkL0JIFmYp-pUV3ZEC6xIMs/edit#heading=h.x36oy5demzwb
