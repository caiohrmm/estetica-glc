package br.com.glc.esteticaglc.controllers;

import br.com.glc.esteticaglc.entities.Cliente;
import br.com.glc.esteticaglc.entities.Produto;
import br.com.glc.esteticaglc.entities.ProdutoServico;
import br.com.glc.esteticaglc.entities.Servico;
import br.com.glc.esteticaglc.services.ClienteService;
import br.com.glc.esteticaglc.services.ServicoService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.omnifaces.cdi.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
@Data
public class ServicoController implements Serializable {

    @Autowired
    private ServicoService servicoService;

    private Servico servico;
    private List<Servico> servicoList;

    private ProdutoServico produtoServico;
    private List<Produto> produtoList;
    private List<ProdutoServico> produtoServicoList;

    @Autowired
    private ClienteService clienteService;

    @PostConstruct
    public void init() {
        servico = new Servico();
        produtoServico = new ProdutoServico();
        produtoServico.setProduto(new Produto());
        produtoServico.setServico(new Servico());

        servicoList = servicoService.listar();
        produtoList = servicoService.listarProdutos();
        produtoServicoList = new ArrayList<>();
    }

    public void adicionarProdutoUtilizado() {
        produtoServicoList.add(produtoServico);
        produtoServico = new ProdutoServico();
    }

    public List<Cliente> buscarCliente(String nome) {
        return clienteService.buscarPorNome(nome);
    }

}
