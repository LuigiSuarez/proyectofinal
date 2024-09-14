package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.dto.reponse.OdontologoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.OdontologoModifyDto;
import com.digitalhouse.proyectofinal.dto.request.OdontologoRequestDto;
import com.digitalhouse.proyectofinal.entity.Odontologo;
import com.digitalhouse.proyectofinal.exception.BadRequestException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.IOdontologoRepository;
import com.digitalhouse.proyectofinal.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class OdontologoService implements IOdontologoService {

    private IOdontologoRepository odontologoRepository;
    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public OdontologoResponseDto guardarOdontologo(OdontologoRequestDto odontologoRequestDto) {
        logger.info("Intentando guardar un odontologo: {}",odontologoRequestDto);
        if(odontologoRequestDto == null || odontologoRequestDto.getNombre() == null || odontologoRequestDto.getApellido() ==null || odontologoRequestDto.getNoMatricula() == 0 ){
            logger.error("Error al guardar el odontologo: datos inválidos.");
            throw new BadRequestException("El odontologo no puede ser nulo y debe tener un nombre, apellido y Nro de " +
                                                  "Matricula");
        }
        Odontologo odontologo    = convertirDtoEnOdontologo(odontologoRequestDto);
        OdontologoResponseDto  savedOdontologo = convertirOdontologoEnResponse(odontologoRepository.save(odontologo));
        logger.info("Odontologo guardado con éxito: {}", savedOdontologo);
        return savedOdontologo;
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        if(odontologo.isPresent()){
            return odontologo;
        }else{
            logger.error("Error al buscar el odontologo: de id:" + id);
            throw new ResourceNotFoundException("El odontologo no se pudo encontrar");
        }

    }


    @Override
    public List<OdontologoResponseDto> buscarTodos() {
        List<Odontologo> odontologosDesdeDB = odontologoRepository.findAll();
        List<OdontologoResponseDto> odontologoListaRespuesta = new ArrayList<>();
        for (Odontologo odontologo : odontologosDesdeDB) {
            OdontologoResponseDto odontologoRespuesta =  convertirOdontologoEnResponse(odontologo);
            logger.info("Odontologo"+ odontologo);
            odontologoListaRespuesta.add(odontologoRespuesta);
        }

        return odontologoListaRespuesta;
    }


    @Override
    public void modificarOdontologo(OdontologoModifyDto odontologoModifyDto) {
        Optional<Odontologo> odontologo = odontologoRepository.findById(odontologoModifyDto.getId());
        if (odontologo.isPresent()) {
            odontologo.get().setNombre(odontologoModifyDto.getNombre());
            odontologo.get().setApellido(odontologoModifyDto.getApellido());
            odontologo.get().setNoMatricula(Integer.parseInt(odontologoModifyDto.getMatricula()));
            odontologoRepository.save(odontologo.get());

    }
    }



    @Override
    public void eliminarOdontologo(Integer id) {
        odontologoRepository.deleteById(id);
    }

    private Odontologo convertirDtoEnOdontologo(OdontologoRequestDto odontologoRequestDto) {
        Odontologo odontologo = new Odontologo();
        odontologo.setNoMatricula(odontologoRequestDto.getNoMatricula());
        odontologo.setNombre(odontologoRequestDto.getNombre());
        odontologo.setApellido(odontologoRequestDto.getApellido());
        return odontologo;
    }

    private OdontologoResponseDto convertirOdontologoEnResponse(Odontologo odontologo){
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto();
        odontologoResponseDto.setId(odontologo.getId());
        odontologoResponseDto.setNombre(odontologo.getNombre());
        odontologoResponseDto.setApellido(odontologo.getApellido());
        odontologoResponseDto.setMatricula(String.valueOf(odontologo.getNoMatricula()));
        return odontologoResponseDto;
    }
}
