package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.enums.FormaPagamento;
import br.com.glc.esteticaglc.entities.enums.StatusPagamento;
import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tb_pagamento")
@Getter
@Setter
public class Pagamento extends GenericDomain {

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
    private Double valorPago;
    private LocalDateTime dataPagamento;

    @ManyToMany
    @JoinTable(name = "tb_pagamento_servico",
            joinColumns = @JoinColumn(name = "codigo_pagamento"),
            inverseJoinColumns = @JoinColumn(name = "codigo_servico"))
    private Set<Servico> servicos = new HashSet<>();

}
