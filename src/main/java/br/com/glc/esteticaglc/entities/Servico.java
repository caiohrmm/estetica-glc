package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.enums.StatusPagamento;
import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_servico")
public class Servico extends GenericDomain {

    private String descricao;

    @Transient
    private Double valorTotalProdutos;

    @Transient
    private Double valorParcial;
    private Double valorTotal;
    private StatusPagamento statusPagamento;
    private LocalDateTime data;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Usuario usuario;

    @ManyToMany(mappedBy = "servicos")
    private Set<Pagamento> pagamentos = new HashSet<>();

    @OneToMany(mappedBy = "servico")
    private Set<ProdutoServico> produtoServicos = new HashSet<>();

}
