package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.Produto;
import br.com.glc.esteticaglc.entities.Servico;
import br.com.glc.esteticaglc.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ProdutoService produtoService;

    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    public List<Produto> listarProdutos() {
        return produtoService.listar();
    }

    public void inserir(Servico servico) {
        servicoRepository.save(servico);
    }

}
