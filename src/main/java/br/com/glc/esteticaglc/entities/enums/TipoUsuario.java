package br.com.glc.esteticaglc.entities.enums;

import lombok.Getter;
import lombok.Setter;

public enum TipoUsuario {

    ADMIN("Administrador"),
    FUNC("Funcionario");

    @Getter @Setter
    private String descricao;

    private TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

}
