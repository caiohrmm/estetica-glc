package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_estoque")
public class Estoque extends GenericDomain {
    private Integer quantidade;
    private Double precoCusto;
    private LocalDateTime dataCompra;

    @OneToMany(mappedBy = "estoque")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;

}
