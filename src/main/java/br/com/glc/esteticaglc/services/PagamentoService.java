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
                // pegar o valor total da lista de pagamentos da classe servico
                Double valorTotalPagamentos = servico.getPagamentos().stream().mapToDouble(Pagamento::getValorPago).sum();
                //relacionamento bidimensional
                servico.getPagamentos().add(pagamento);
                pagamento.getServicos().add(servico);
                // adiciona o valor do novo pagamento
                valorTotalPagamentos += pagamento.getValorPago();

                if (valorTotalPagamentos >= servico.getValorTotal()) {
                    servico.setStatusPagamento(StatusPagamento.PAGO);
                }
                pagamentoRepository.save(pagamento);
                servicoRepository.save(servico);
            }
        }
    }

    public void excluir() {
    }

    public void atualizarPagamento() {

    }

}
