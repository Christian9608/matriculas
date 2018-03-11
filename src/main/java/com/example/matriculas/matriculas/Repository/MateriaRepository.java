package com.example.matriculas.matriculas.Repository;

import com.example.matriculas.matriculas.Modelo.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {
    List<Materia> findByDeleted(Boolean deleted);
    Materia findByIdMateriaInAndDeletedIn(int idDetalleMatricula, Boolean deleted);
}
