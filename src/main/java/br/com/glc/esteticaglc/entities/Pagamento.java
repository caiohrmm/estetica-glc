package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.enums.FormaPagamento;
import br.com.glc.esteticaglc.entities.enums.StatusPagamento;
import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tb_pagamento")
@Data
public class Pagamento extends GenericDomain {

    private FormaPagamento formaPagamento;
    private StatusPagamento status;
    private Double valorPago;
    private LocalDateTime dataPagamento;

    @ManyToMany
    @JoinTable(name = "pagamento_servico",
            joinColumns = @JoinColumn(name = "codigo_pagamento"),
            inverseJoinColumns = @JoinColumn(name = "codigo_servico"))
    private Set<Servico> servicos = new HashSet<>();

}
