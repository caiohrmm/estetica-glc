package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;

@Getter
@Setter
@Entity
@Table(name = "tb_produto")
public class Produto extends GenericDomain {
    private String nome;
    private String descricao;
    private Double precoUnitario;
    private Integer quantidadeMinima;
    private Double precoCusto;
    private Integer quantidadeEstoque;
    private LocalDate dataAlteracao;
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "produto")
    private HashSet<ProdutoServico> produtoServicos = new HashSet<>();
    @OneToMany(mappedBy = "produto")
    private HashSet<HistoricoProduto> historicoProdutos = new HashSet<>();
}
