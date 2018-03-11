package com.example.matriculas.matriculas.Repository;

import com.example.matriculas.matriculas.Modelo.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    List<Matricula> findByDeleted(Boolean deleted);
    Matricula findByIdMatriculaInAndDeletedIn(int idMatricula, Boolean deleted);
}
