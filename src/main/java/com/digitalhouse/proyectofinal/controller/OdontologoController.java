package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.entity.Odontologo;
import com.digitalhouse.proyectofinal.service.OdontologoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public Odontologo guardarOdontologo(@RequestBody Odontologo odontologo){
        return odontologoService.guardar(odontologo);
    }
    @GetMapping("/buscartodos")
    public List<Odontologo> listarTodos(){
        return odontologoService.listar();
    }
    @PutMapping("/modificar")
    public String modificarOdontologo(@RequestBody Odontologo odontologo){
        odontologoService.modificarOdontologo(odontologo);
        return "El odontologo "+ odontologo.getId() + " fue modificado exitosamente";
    }


}
