package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

}
