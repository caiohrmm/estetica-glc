package br.com.glc.esteticaglc.entities;

import br.com.glc.esteticaglc.entities.enums.TipoUsuario;
import br.com.glc.esteticaglc.entities.utils.GenericDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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

}
