package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.Pagamento;
import br.com.glc.esteticaglc.entities.Servico;
import br.com.glc.esteticaglc.entities.enums.StatusPagamento;
import br.com.glc.esteticaglc.repositories.PagamentoRepository;
import br.com.glc.esteticaglc.repositories.ServicoRepository;
import br.com.glc.esteticaglc.utils.GrowlView;
import br.com.glc.esteticaglc.utils.enums.MessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ServicoRepository servicoRepository;


    public void adicionarPagamento(Long idServico, Pagamento pagamento) {
        Servico servico = servicoRepository.findById(idServico).orElse(null);
        if (servico != null && pagamento != null) {
            if (servico.getValorTotal() < pagamento.getValorPago()) {
                GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "O valor do pagamento é maior que o valor total do serviço!");
            } else {
                pagamento.setDataPagamento(LocalDateTime.now());
                // pegar o valor total da lista de pagamentos da classe servico
                Double valorTotalPagamentos = servico.getPagamentos().stream().mapToDouble(Pagamento::getValorPago).sum();
                //relacionamento bidimensional
                servico.getPagamentos().add(pagamento);
                pagamento.getServicos().add(servico);
                // adiciona o valor do novo pagamento
                valorTotalPagamentos += pagamento.getValorPago();

                if (valorTotalPagamentos >= servico.getValorTotal()) {
                    pagamento.setStatus(StatusPagamento.PAGO);
                    servico.setStatusPagamento(StatusPagamento.PAGO);
                }
                GrowlView.showInfo(MessageEnum.MSG_SUCESSO.getMsg(), "Pagamento registrado para o servico: " + servico.getDescricao());
                pagamentoRepository.save(pagamento);
                servicoRepository.save(servico);
            }
        }
    }

    public void excluir(Pagamento pagamento) {
        for (Servico servico : pagamento.getServicos()) {
            servico.getPagamentos().remove(pagamento);
            servicoRepository.save(servico);
        }
        pagamentoRepository.delete(pagamento);
    }

    public void atualizarPagamento(Long codigoPagamentoAntigo, Pagamento pagamentoAtualizado) {
        Pagamento pagamentoAntigo = pagamentoRepository.findById(codigoPagamentoAntigo).orElse(null);
        if (pagamentoAntigo == null) {
            GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "Pagamento com o código: " + codigoPagamentoAntigo + " não encontrado.");
        } else {
            Double valorRestante = 0.0;
            Servico servico = pagamentoAntigo.getServicos().iterator().next();
            Double valorTotalPago = servico.getPagamentos().stream().mapToDouble(Pagamento::getValorPago).sum();
            valorRestante = servico.getValorTotal() - valorTotalPago;
            if (pagamentoAtualizado.getValorPago() < valorRestante) {
                atualizarDados(pagamentoAntigo, pagamentoAtualizado);
                pagamentoAntigo.setStatus(StatusPagamento.ABERTO);
                pagamentoRepository.save(pagamentoAntigo);
            } else if (pagamentoAtualizado.getValorPago() == valorRestante) {
                atualizarDados(pagamentoAntigo, pagamentoAtualizado);
                pagamentoAntigo.setStatus(StatusPagamento.PAGO);
                pagamentoRepository.save(pagamentoAntigo);
            } else if (pagamentoAtualizado.getValorPago() > valorTotalPago) {
                GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), " o valor pago é maior que o saldo devedor.");
            }
        }
    }
    
    public void atualizarDados(Pagamento pagamentoAntigo, Pagamento pagamentoAtualizado) {
        pagamentoAntigo.setValorPago(pagamentoAtualizado.getValorPago());
    }

}
