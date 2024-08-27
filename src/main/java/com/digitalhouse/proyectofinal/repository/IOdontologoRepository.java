package com.digitalhouse.proyectofinal.repository;

import com.digitalhouse.proyectofinal.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {

}
