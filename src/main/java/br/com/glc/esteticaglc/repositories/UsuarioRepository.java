package br.com.glc.esteticaglc.repositories;

import br.com.glc.esteticaglc.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
