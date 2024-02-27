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
import java.util.List;

@Component
@ViewScoped
@Data
public class ClienteController implements Serializable {

    @Autowired
    private ClienteService service;
    private Cliente cliente;

    private List<Cliente> clienteList;

    @PostConstruct
    public void init() {
        cliente = new Cliente();
        cliente.setEndereco(new Endereco());
        cliente.setServicos(new HashSet<>());

        clienteList = service.listar();
    }

    public void salvar() {
        service.salvar(cliente);
        clienteList = service.listar();
    }

    public void editar() {
        service.editar(cliente);
        clienteList = service.listar();
    }

    public void delete() {
        service.delete(cliente);
        clienteList = service.listar();
    }

    public void exportarParaExcel() {
        service.exportarParaExcel();
    }

}
