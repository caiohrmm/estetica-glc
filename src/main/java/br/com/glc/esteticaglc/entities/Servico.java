package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "tp_produto_servico", joinColumns = @JoinColumn(name = "codigo_servico"), inverseJoinColumns = @JoinColumn(name = "codigo_produto"))
    private List<Produto> produtos = new ArrayList<>();


}
