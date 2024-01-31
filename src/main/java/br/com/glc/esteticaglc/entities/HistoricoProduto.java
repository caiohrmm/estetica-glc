package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tb_historico_produto")
public class HistoricoProduto extends GenericDomain {
    private Double valorUnitarioAnterior;
    private LocalDate dataAlteracao;
    private Integer quantidadeAnterior;
    private String nomeAnterior;
    private Double precoDeCustoAnterior;

    public HistoricoProduto() {
    }



    @ManyToOne
    @JoinColumn(name = "codigo_produto")
    private Produto produto;


}
