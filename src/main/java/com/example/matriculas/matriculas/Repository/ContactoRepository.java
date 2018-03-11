package com.example.matriculas.matriculas.Repository;

import com.example.matriculas.matriculas.Modelo.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
    List<Contacto> findByDeleted(Boolean deleted);
    Contacto findByIdContactoInAndDeletedIn(int idContacto, Boolean deleted);
}
