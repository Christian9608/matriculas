package com.example.matriculas.matriculas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.matriculas.matriculas.Modelo.Persona;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Integer>{
    List<Persona> findByDeleted(Boolean deleted);
    Persona findByIdPersonaInAndDeletedIn(int idPersona, Boolean deleted);
}
