package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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

}
