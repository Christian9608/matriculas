package com.example.matriculas.matriculas.Repository;

import com.example.matriculas.matriculas.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByDeleted(Boolean deleted);
    Usuario findByIdUsuarioInAndDeletedIn(int idUsuario, Boolean deleted);
}
