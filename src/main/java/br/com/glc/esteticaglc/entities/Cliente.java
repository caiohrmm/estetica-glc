package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
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

    @ManyToOne
    private Usuario usuario;

    public String enderecoCompleto() {
        String enderecoCompleto = null;

        if (endereco != null) {
            if (endereco.getRua() != null && !endereco.getRua().isBlank()) {
                enderecoCompleto = endereco.getRua();
            }

            if (endereco.getBairro() != null && !endereco.getBairro().isBlank()) {
                enderecoCompleto = enderecoCompleto + ", " + endereco.getBairro();
            }

            if (endereco.getNumero() != null && !endereco.getNumero().isBlank()) {
                enderecoCompleto = enderecoCompleto + ", " + endereco.getNumero();
            }

            if (endereco.getCidade() != null && !endereco.getCidade().isBlank()) {
                enderecoCompleto = enderecoCompleto + ", " + endereco.getCidade();
            }
        }

        return enderecoCompleto;
    }

}
