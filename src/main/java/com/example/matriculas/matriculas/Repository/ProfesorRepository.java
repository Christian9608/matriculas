package com.example.matriculas.matriculas.Repository;

import com.example.matriculas.matriculas.Modelo.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
    List<Profesor> findByDeleted(Boolean deleted);
    Profesor findByIdProfesorInAndDeletedIn(int idProfesor, Boolean deleted);
}
