package com.digitalhouse.proyectofinal.controller;

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
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Odontologo>> buscarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoId(@PathVariable Integer id){
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarPorId(id);
        if (odontologoEncontrado.isPresent()) {
            return ResponseEntity.ok(odontologoEncontrado.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/modificar")
    public ResponseEntity<String> modificarOdontologo(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologEncontrado = odontologoService.buscarPorId(odontologo.getId());
        if (odontologEncontrado.isPresent()) {
            odontologoService.modificarOdontologo(odontologo);
            String jsonResponse = "Odontologo modificado con exito";
            return ResponseEntity.ok(jsonResponse);
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id){
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarPorId(id);
        if (odontologoEncontrado.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            String jsonRsponse = "Odontologo eliminado con exito";
            return ResponseEntity.ok(jsonRsponse);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
