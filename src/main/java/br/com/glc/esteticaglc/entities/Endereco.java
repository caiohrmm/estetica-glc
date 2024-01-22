package br.com.glc.esteticaglc.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Endereco {

    private String rua;
    private String bairro;
    private String numero;
    private String cidade;

}
