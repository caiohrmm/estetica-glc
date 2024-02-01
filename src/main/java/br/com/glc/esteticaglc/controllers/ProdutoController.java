package br.com.glc.esteticaglc.controllers;

import br.com.glc.esteticaglc.entities.HistoricoProduto;
import br.com.glc.esteticaglc.entities.Produto;
import br.com.glc.esteticaglc.entities.Usuario;
import br.com.glc.esteticaglc.services.HistoricoProdutoService;
import br.com.glc.esteticaglc.services.ProdutoService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.omnifaces.cdi.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ViewScoped
@Component
@Data
public class ProdutoController implements Serializable {
    @Autowired
    private ProdutoService produtoService;

    Produto produto;

    private List<Produto> produtosList;

    @Autowired
    private HistoricoProdutoService historicoProdutoService;
    private List<HistoricoProduto> historicoProdutos;


    @PostConstruct
    public void init() {
        produto = new Produto();
        produto.setHistoricoProdutos(new HashSet<>());
        produto.setUsuario(new Usuario());
        produto.setProdutoServicos(new HashSet<>());

        produtosList = produtoService.listar();
        historicoProdutos = historicoProdutoService.listar();
    }

    public void salvar() {
        produtoService.inserir(produto);
        produtosList = produtoService.listar();
    }

    public void editar() {
        produtoService.atualizar(produto.getCodigo(), produto);
        produtosList = produtoService.listar();
        historicoProdutos = historicoProdutoService.listar();
    }

    public void excluir() {
        produtoService.excluir(produto);
        produtosList = produtoService.listar();
    }


}
