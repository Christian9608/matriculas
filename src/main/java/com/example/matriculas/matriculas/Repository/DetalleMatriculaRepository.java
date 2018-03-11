package com.example.matriculas.matriculas.Repository;

import com.example.matriculas.matriculas.Modelo.DetalleMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleMatriculaRepository extends JpaRepository<DetalleMatricula, Integer> {
    List<DetalleMatricula> findByDeleted(Boolean deleted);
    DetalleMatricula findByIdDetalleMatriculaInAndDeletedIn(int idDetalleMatricula, Boolean deleted);
}
