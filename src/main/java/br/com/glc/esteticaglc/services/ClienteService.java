package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.Cliente;
import br.com.glc.esteticaglc.repositories.ClienteRepository;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void salvar(Cliente cliente) {
        if (clienteRepository.clienteExistente(cliente.getCpf()) != null) {
            Messages.addFlashGlobalError("CPF já cadastrado.");
        } else {
            clienteRepository.save(cliente);
            Messages.addFlashGlobalInfo("Cliente cadastrado com sucesso.");
        }
    }

}
