package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @JoinColumn(name = "codigo_estoque")
    private Estoque estoque;

    @ManyToMany()
    @JoinTable(name = "tb_produto_servico", joinColumns = {@JoinColumn(name = "produto_codigo")},
            inverseJoinColumns = {@JoinColumn(name = "servico_codigo")})
    private Set<Servico> servicos = new HashSet<>();

}
