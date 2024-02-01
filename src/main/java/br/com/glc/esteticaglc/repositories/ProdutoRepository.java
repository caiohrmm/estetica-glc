package br.com.glc.esteticaglc.repositories;

import br.com.glc.esteticaglc.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT P FROM Produto P WHERE P.nome = :nomeProduto")
    Produto findByNome(@Param(value = "nomeProduto") String nome);

    @Query("SELECT P From Produto P WHERE P.ativo = true")
    List<Produto> findAll();
}
