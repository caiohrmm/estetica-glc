package br.com.glc.esteticaglc.repositories;

import br.com.glc.esteticaglc.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
