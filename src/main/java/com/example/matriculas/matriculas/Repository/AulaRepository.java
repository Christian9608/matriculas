package com.example.matriculas.matriculas.Repository;

import com.example.matriculas.matriculas.Modelo.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Integer> {
    List<Aula> findByDeleted(Boolean deleted);
    Aula findByIdAulaInAndDeletedIn(int idAula, Boolean deleted);
}
