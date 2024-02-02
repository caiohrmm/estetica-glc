package br.com.glc.esteticaglc.repositories;

import br.com.glc.esteticaglc.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %?1%")
    List<Cliente> findByNome(String nome);

    @Query("SELECT c FROM Cliente c WHERE c.cpf = ?1")
    Cliente clienteExistente(String cpf);

}
