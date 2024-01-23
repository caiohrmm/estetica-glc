package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_produto")
public class Produto extends GenericDomain {
    private String nome;
    private String descricao;
    private Double precoUnitario;
    private Integer quantidadeMinima;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idEstoque")
    private Estoque estoque;

    @ManyToMany
    private List<Servico> listaServicos;
}
