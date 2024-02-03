package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.Produto;
import br.com.glc.esteticaglc.entities.ProdutoServico;
import br.com.glc.esteticaglc.entities.Servico;
import br.com.glc.esteticaglc.repositories.ServicoRepository;
import br.com.glc.esteticaglc.utils.GrowlView;
import br.com.glc.esteticaglc.utils.enums.MessageEnum;
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

        GrowlView.showInfo(MessageEnum.MSG_SUCESSO.getMsg(), MessageEnum.MSG_SALVO_SUCESSO.getMsg());
    }

    public Double calcularValorTotalProdutos(List<ProdutoServico> produtoServicoList) {
        Double valorTotalProdutos = 0.0;

        for (ProdutoServico produtoSelecionado : produtoServicoList) {
            valorTotalProdutos += produtoSelecionado.getProduto().getPrecoUnitario() * produtoSelecionado.getQuantidade();
        }

        return valorTotalProdutos;
    }

    public Double calcularValorTotal(Servico servico) {
        Double valorTotal = servico.getValorTotalProdutos() + servico.getValorParcial();
        return valorTotal;
    }

}
