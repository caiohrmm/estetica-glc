package br.com.glc.esteticaglc.repositories;

import br.com.glc.esteticaglc.entities.HistoricoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoricoProdutoRepository extends JpaRepository<HistoricoProduto, Long> {

    @Query("SELECT hp FROM HistoricoProduto hp WHERE hp.produto.ativo = true")
    List<HistoricoProduto> findAll();

}
