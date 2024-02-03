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
import org.primefaces.event.FlowEvent;
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

    private boolean skip;

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

    public void salvar() {
        servicoService.inserir(servico);
    }

    public void adicionarProdutoUtilizado() {
        produtoServicoList = servicoService.adicionarProdutoUtilizado(produtoServico, produtoServicoList);
        produtoServico = new ProdutoServico();

        servico.setValorParcial(null);
        servico.setValorTotal(null);
        calcularValorTotalProdutos();
    }

    public void removerProdutoUtilizado(ProdutoServico produtoServico) {
        produtoServicoList = servicoService.removerProdutoUtilizado(produtoServico, produtoServicoList);
    }

    public List<Cliente> buscarCliente(String nome) {
        return clienteService.buscarPorNome(nome);
    }

    public void calcularValorTotalProdutos() {
        servico.setValorTotalProdutos(servicoService.calcularValorTotalProdutos(produtoServicoList));
    }

    public void calcularValorTotal() {
        servico.setValorTotal(servicoService.calcularValorTotal(servico));
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

}
