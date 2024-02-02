package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.Cliente;
import br.com.glc.esteticaglc.repositories.ClienteRepository;
import br.com.glc.esteticaglc.utils.GrowlView;
import br.com.glc.esteticaglc.utils.enums.MessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorCodigo(Long codigo) {
        return clienteRepository.findById(codigo).get();
    }

    public List<Cliente> buscarPorNome(String nome) {
        List<Cliente> clientesEncontrados = clienteRepository.findByNome(nome);

        if (clientesEncontrados.isEmpty()) {
            GrowlView.showWarn(MessageEnum.MSG_AVISO.getMsg(), "Cliente não encontrado");
        }
        return clientesEncontrados;
    }

    public void salvar(Cliente cliente) {
        validaCliente(cliente);
    }

    public void editar(Cliente cliente) {
        validaCliente(cliente);
    }

    public void delete(Cliente cliente) {
        Cliente clienteRecuperado = clienteRepository.findById(cliente.getCodigo()).get();

        clienteRepository.delete(clienteRecuperado);
        GrowlView.showInfo(MessageEnum.MSG_SUCESSO.getMsg(), MessageEnum.MSG_EXCLUIDO_SUCESSO.getMsg());
    }

    public void validaCliente(Cliente cliente) {
        if (clienteRepository.clienteExistente(cliente.getCpf()) != null && !Objects.equals(cliente.getCodigo(), clienteRepository.clienteExistente(cliente.getCpf()).getCodigo())) {
            GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "CPF já cadastrado.");
            // clienteRepository.saveAndFlush(cliente);
        } else {
            clienteRepository.saveAndFlush(cliente);
            GrowlView.showInfo(MessageEnum.MSG_SUCESSO.getMsg(), MessageEnum.MSG_SALVO_SUCESSO.getMsg());
        }
    }
}
