package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.model.OdontologoModel;
import com.digitalhouse.proyectofinal.model.Paciente;
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
    public OdontologoModel guardarOdontologo(@RequestBody OdontologoModel odontologo){
        return odontologoService.guardar(odontologo);
    }
    @GetMapping("/buscartodos")
    public List<OdontologoModel> listarTodos(){
        return odontologoService.listar();
    }
    @PutMapping("/modificar")
    public String modificarOdontologo(@RequestBody OdontologoModel odontologo){
        odontologoService.modificarOdontologo(odontologo);
        return "El odontologo "+ odontologo.getId() + " fue modificado exitosamente";
    }


}
