package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.entity.Paciente;
import com.digitalhouse.proyectofinal.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/guardar")
    public Paciente guardarPaciente(@RequestBody Paciente paciente){
        return pacienteService.guardarPaciente(paciente);
    }

    @GetMapping("/buscartodos")
    public List<Paciente> listarTodos(){
        return pacienteService.buscarTodos();
    }

    @PutMapping("/modificar")
    public String modificarPaciente(@RequestBody Paciente paciente){
        pacienteService.modificarPaciente(paciente);
        return "el paciente "+ paciente.getId() + " fue modificado";
    }

    @DeleteMapping("/borrar/{id}")
    public String borrarPaciente(@PathVariable Integer id){
        pacienteService.borrarPaciente(id);
        return "Paciente con ID " + id + " fue eliminado exitosamente.";
    }
}
