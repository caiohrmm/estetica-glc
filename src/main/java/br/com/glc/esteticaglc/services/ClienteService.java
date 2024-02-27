package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.Cliente;
import br.com.glc.esteticaglc.repositories.ClienteRepository;
import br.com.glc.esteticaglc.utils.ExcelExporter;
import br.com.glc.esteticaglc.utils.GrowlView;
import br.com.glc.esteticaglc.utils.enums.MessageEnum;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

@Component
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    @Autowired
    private ExcelExporter excelExporter;

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

    public void exportarParaExcel() {
        if (clienteRepository.findAll().isEmpty()) {
            GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "Não existem dados de clientes para exportar no momento!");
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

            try {
                // Gera o arquivo Excel
                ByteArrayOutputStream outputStream = excelExporter.exportarClienteParaExcel(clienteRepository.findAll());

                // Configura os headers para download
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=\"relatorio_clientes.xls\"");
                response.setContentLength(outputStream.size());

                // Escreve o conteúdo do arquivo no HttpServletResponse
                OutputStream responseOutputStream = externalContext.getResponseOutputStream();
                outputStream.writeTo(responseOutputStream); // Escreve o conteúdo do outputStream no responseOutputStream
                responseOutputStream.flush();
                facesContext.responseComplete();
            } catch (IOException e) {
                e.printStackTrace();
                // Lidar com erros de exportação, se necessário
                GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "Erro ao exportar arquivo.");
            }
        }


    }




}
