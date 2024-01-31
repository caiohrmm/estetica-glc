package br.com.glc.esteticaglc.controllers;

import br.com.glc.esteticaglc.entities.Produto;
import br.com.glc.esteticaglc.entities.Usuario;
import br.com.glc.esteticaglc.services.ProdutoService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.omnifaces.cdi.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

@ViewScoped
@Component
@Data
public class ProdutoController implements Serializable {
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private LoginController loginController;

    Produto produto;

    private List<Produto> produtosList;
    private Long codigoProdutoAntigo;


    @PostConstruct
    public void init() {
        produto = new Produto();
        produto.setHistoricoProdutos(new HashSet<>());
        produto.setUsuario(new Usuario());
        produto.setProdutoServicos(new HashSet<>());

    }

    public void salvar() {
        Long codigoUsuario = loginController.getUsuarioLogado().getCodigo();
        produtoService.inserir(codigoUsuario, produto);
        produtosList = produtoService.listar();
    }

    public void editar() {
        produtoService.atualizar(codigoProdutoAntigo, produto);
        produtosList = produtoService.listar();
    }



}
