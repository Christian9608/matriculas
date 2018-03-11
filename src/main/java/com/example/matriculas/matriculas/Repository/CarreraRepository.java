package com.example.matriculas.matriculas.Repository;

import com.example.matriculas.matriculas.Modelo.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {
    List<Carrera> findByDeleted(Boolean deleted);
    Carrera findByIdCarreraInAndDeletedIn(int idCarrera, Boolean deleted);
}
