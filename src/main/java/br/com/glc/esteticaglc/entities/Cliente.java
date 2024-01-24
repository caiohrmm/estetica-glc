package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_cliente")
public class Cliente extends GenericDomain {

    private String nome;
    private String cpf;
    private String rg;
    private String telefone;

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente")
    private Set<Servico> servicos = new HashSet<>();

}
