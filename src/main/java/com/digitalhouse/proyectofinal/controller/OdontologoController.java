package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.dto.reponse.OdontologoResponseDto;
import com.digitalhouse.proyectofinal.dto.reponse.TurnoModifyDto;
import com.digitalhouse.proyectofinal.dto.reponse.TurnoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.OdontologoModifyDto;
import com.digitalhouse.proyectofinal.dto.request.OdontologoRequestDto;
import com.digitalhouse.proyectofinal.entity.Odontologo;
import com.digitalhouse.proyectofinal.service.impl.OdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<OdontologoResponseDto> guardarOdontologo(@RequestBody OdontologoRequestDto odontologoRequestDto){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologoRequestDto));
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<OdontologoResponseDto>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoId(@PathVariable Integer id){
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarPorId(id);
         return  ResponseEntity.ok(odontologoEncontrado.get());
    }


    @PutMapping("/modificar")
    public ResponseEntity<String> modificarOdontologo(@RequestBody OdontologoModifyDto odontologoModifyDto){
        odontologoService.modificarOdontologo(odontologoModifyDto);
        return ResponseEntity.ok("{\"mensaje\": \"El odontologo fue modificado\"}");
    }



    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id){
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("{\"mensaje\": \"El odontologo fue eliminado\"}");
    }


}
