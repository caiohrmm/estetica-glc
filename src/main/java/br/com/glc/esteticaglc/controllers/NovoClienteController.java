package br.com.glc.esteticaglc.controllers;

import br.com.glc.esteticaglc.entities.Cliente;
import br.com.glc.esteticaglc.entities.Endereco;
import br.com.glc.esteticaglc.services.ClienteService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.omnifaces.cdi.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;

@Component
@ViewScoped
@Data
public class NovoClienteController implements Serializable {

    @Autowired
    private ClienteService service;
    private Cliente cliente;

    @PostConstruct
    public void init() {
        cliente = new Cliente();
        cliente.setEndereco(new Endereco());
        cliente.setServicos(new HashSet<>());
    }

    public void salvar() {
        service.salvar(cliente);
    }

}
