package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.Cliente;
import br.com.glc.esteticaglc.repositories.ClienteRepository;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public void salvar(Cliente cliente) {
        if (clienteRepository.clienteExistente(cliente.getCpf()) != null) {
            Messages.addFlashGlobalError("CPF já cadastrado.");
            clienteRepository.saveAndFlush(cliente);
        } else {
            clienteRepository.save(cliente);
            Messages.addFlashGlobalInfo("Registro salvo com sucesso.");
        }
    }

    public void editar(Cliente cliente) {
        clienteRepository.saveAndFlush(cliente);

        Messages.addFlashGlobalInfo("Registro salvo com sucesso.");
    }

    public void delete(Cliente cliente) {
        Cliente clienteRecuperado = clienteRepository.findById(cliente.getCodigo()).get();

        clienteRepository.delete(clienteRecuperado);
        Messages.addFlashGlobalInfo("Registro deletado com sucesso.");
    }

}
