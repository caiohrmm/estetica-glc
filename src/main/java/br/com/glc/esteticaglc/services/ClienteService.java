package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.Cliente;
import br.com.glc.esteticaglc.repositories.ClienteRepository;
import br.com.glc.esteticaglc.utils.GrowlView;
import br.com.glc.esteticaglc.utils.enums.MessageEnum;
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
            GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "CPF já cadastrado.");
            clienteRepository.saveAndFlush(cliente);
        } else {
            clienteRepository.save(cliente);
            GrowlView.showInfo(MessageEnum.MSG_SUCESSO.getMsg(), MessageEnum.MSG_SALVO_SUCESSO.getMsg());
        }
    }

    public void editar(Cliente cliente) {
        clienteRepository.saveAndFlush(cliente);

        GrowlView.showInfo(MessageEnum.MSG_SUCESSO.getMsg(), MessageEnum.MSG_SALVO_SUCESSO.getMsg());
    }

    public void delete(Cliente cliente) {
        Cliente clienteRecuperado = clienteRepository.findById(cliente.getCodigo()).get();

        clienteRepository.delete(clienteRecuperado);
        GrowlView.showWarn(MessageEnum.MSG_SUCESSO.getMsg(), MessageEnum.MSG_EXCLUIDO_SUCESSO.getMsg());
    }

}
