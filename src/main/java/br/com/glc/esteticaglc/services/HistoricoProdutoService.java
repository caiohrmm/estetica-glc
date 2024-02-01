package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.HistoricoProduto;
import br.com.glc.esteticaglc.repositories.HistoricoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoricoProdutoService {

    @Autowired
    private HistoricoProdutoRepository repository;

    public List<HistoricoProduto> listar() {
        return repository.findAll();
    }

}
