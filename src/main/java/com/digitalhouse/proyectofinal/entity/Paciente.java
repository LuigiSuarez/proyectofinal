package com.digitalhouse.proyectofinal.entity;
//Era el model
import com.digitalhouse.proyectofinal.utils.GsonProvider;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String apellido;
    private String nombre;
    private String dni;
    private LocalDate fechaIngreso;

    //@ManyToOne
    //private Domicilio domicilio;

    @OneToOne(cascade  = CascadeType.ALL)
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "paciente-turno")

    //private Set<Turno> turnoSet;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }
}
