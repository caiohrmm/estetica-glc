package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_servico")
public class Servico extends GenericDomain {

    private String descricao;
    private Double valorTotal;
    private Boolean pago;
    private LocalDateTime data;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "produto_servico", joinColumns = @JoinColumn(name = "idServico"), inverseJoinColumns = @JoinColumn(name = "idProduto"))
    private List<Produto> listaProdutos;

}
