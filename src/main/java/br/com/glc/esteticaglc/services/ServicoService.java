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

    public List<ProdutoServico> adicionarProdutoUtilizado(ProdutoServico produtoServico, List<ProdutoServico> produtoServicoList) {
        if (produtoServico.getProduto() == null) {
            GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "Selecione o produto.");
        } else if (produtoServico.getQuantidade() == null) {
            GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "Selecione a quantidade.");
        } else {
            produtoServicoList.add(produtoServico);
            GrowlView.showInfo(MessageEnum.MSG_SUCESSO.getMsg(), "Produto adicionado.");
            return produtoServicoList;
        }
        return produtoServicoList;
    }

    public List<ProdutoServico> removerProdutoUtilizado(ProdutoServico produtoServico, List<ProdutoServico> produtoServicoList) {
        produtoServicoList.remove(produtoServico);
        GrowlView.showWarn(MessageEnum.MSG_SUCESSO.getMsg(), "Produto removido.");

        return produtoServicoList;
    }

    public Double calcularValorTotalProdutos(List<ProdutoServico> produtoServicoList) {
        Double valorTotalProdutos = 0.0;

        for (ProdutoServico produtoSelecionado : produtoServicoList) {
            valorTotalProdutos += (produtoSelecionado.getProduto().getPrecoUnitario() * produtoSelecionado.getQuantidade());
        }
        return valorTotalProdutos;
    }

    public Double calcularValorTotal(Servico servico) {
        Double valorTotal = 0.0;

        if (servico.getValorTotalProdutos() != null) {
            valorTotal = servico.getValorTotalProdutos() + servico.getValorParcial();
            return valorTotal;
        }
        return valorTotal = servico.getValorParcial();
    }

}
