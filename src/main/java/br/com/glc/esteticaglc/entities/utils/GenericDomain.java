package br.com.glc.esteticaglc.entities.utils;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@MappedSuperclass
public class GenericDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;

    @Override
    public String toString() {
        return String.format("%s[codigo%d]", getClass().getSimpleName(), getCodigo());
    }

}
