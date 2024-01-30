package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.enums.TipoUsuario;
import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tb_usuario")
public class Usuario extends GenericDomain {

    private String nome;
    private String username;
    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @OneToMany(mappedBy = "usuario")
    private Set<Servico> servicos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    private Set<Produto> produtos = new HashSet<>();
}
