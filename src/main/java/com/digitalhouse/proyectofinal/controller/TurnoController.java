package com.digitalhouse.proyectofinal.controller;

import com.digitalhouse.proyectofinal.dto.reponse.TurnoModifyDto;
import com.digitalhouse.proyectofinal.dto.reponse.TurnoResponseDto;
import com.digitalhouse.proyectofinal.dto.request.TurnoRequestDto;
import com.digitalhouse.proyectofinal.service.ITurnoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<TurnoResponseDto> guardarTurno(@RequestBody TurnoRequestDto turnoRequestDto){
        return ResponseEntity.ok(turnoService.guardarTurno(turnoRequestDto));
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/buscarTurnoApellido/{apellido}")
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorApellido(@PathVariable String apellido){
        Optional<TurnoResponseDto> turno = turnoService.buscarTurnosPorPaciente(apellido);
        return ResponseEntity.ok(turno.get());
    }

    @GetMapping("/buscarPorRangoFechas")
    public ResponseEntity<List<TurnoResponseDto>> buscarTurnosPorRangoDeFechas(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {
        List<TurnoResponseDto> turnos = turnoService.buscarTurnosPorRangoDeFechas(LocalDate.parse(fechaInicio), LocalDate.parse(fechaFin));
        return ResponseEntity.ok(turnos);
    }

    @GetMapping("/buscarPorApellidoOdontologo/{apellido}")
    public ResponseEntity<TurnoResponseDto> buscarTurnosPorApellidoOdontologo(@PathVariable String apellido) {
        Optional<TurnoResponseDto> turnos = turnoService.buscarTurnosPorApellidoOdontologo(apellido);
        return ResponseEntity.ok(turnos.get());
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> modificarTurno(@RequestBody TurnoModifyDto turnoModifyDto){
        turnoService.modificarTurno(turnoModifyDto);
        return ResponseEntity.ok("{\"mensaje\": \"El turno fue modificado\"}");
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id){
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("{\"mensaje\": \"El turno fue eliminado\"}");
    }
}
