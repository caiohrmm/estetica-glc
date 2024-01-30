package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "tb_produto_servico")
public class ProdutoServico extends GenericDomain {
    private Integer quantidade;
    private Double precoProduto;
    @ManyToOne
    @JoinColumn(name = "codigo_produto")
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "codigo_servico")
    private Servico servico;

}
