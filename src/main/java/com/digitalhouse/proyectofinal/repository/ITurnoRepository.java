package com.digitalhouse.proyectofinal.repository;

import com.digitalhouse.proyectofinal.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno,Integer> {

}
