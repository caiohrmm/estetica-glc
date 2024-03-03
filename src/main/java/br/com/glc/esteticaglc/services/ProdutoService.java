package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.HistoricoProduto;
import br.com.glc.esteticaglc.entities.Produto;
import br.com.glc.esteticaglc.entities.Usuario;
import br.com.glc.esteticaglc.repositories.HistoricoProdutoRepository;
import br.com.glc.esteticaglc.repositories.ProdutoRepository;
import br.com.glc.esteticaglc.utils.ExcelExporter;
import br.com.glc.esteticaglc.utils.GrowlView;
import br.com.glc.esteticaglc.utils.enums.MessageEnum;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Component
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private HistoricoProdutoRepository historicoProdutoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ExcelExporter excelExporter;

    public void excluir(Produto produto) {
        produto.setAtivo(false);
        produtoRepository.save(produto);
        GrowlView.showWarn(MessageEnum.MSG_SUCESSO.getMsg(), MessageEnum.MSG_EXCLUIDO_SUCESSO.getMsg());
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public void inserir(Produto novoProduto) {
        if (produtoRepository.findByNome(novoProduto.getNome().toUpperCase()) == null) {
            novoProduto.setDataCriacao(LocalDate.now());

            //Buscando Usuário que registrou produto
            Usuario usuario = usuarioService.recuperarUsuario();

            //Setando estoque inicial para 0 e tornando o Produto ativo
            novoProduto.setQuantidadeEstoque(0);
            novoProduto.setAtivo(true);

            //Criando relacionamentos bidirecional para as entidades envolvidas
            novoProduto.setUsuario(usuario);
            //usuario.getProdutos().add(novoProduto);

            //Persistindo alterações no banco de dados
            usuarioService.salvar(usuario);
            produtoRepository.save(novoProduto);
            GrowlView.showInfo(MessageEnum.MSG_SUCESSO.getMsg(), MessageEnum.MSG_SALVO_SUCESSO.getMsg());

            //Exibir mensagem de aviso de quantidadeEstoque menor que a quantidadeMinima
            GrowlView.showWarn(MessageEnum.MSG_AVISO.getMsg(), "Quantidade em Estoque menor que o Estoque Mínimo.");
        } else {
            GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "Produto já cadastrado.");
        }
    }

    public void atualizar(Long codigoProdutoAntigo, Produto produtoAtualizado) {
        Produto produtoAntigo = produtoRepository.findById(codigoProdutoAntigo).get();

        //Criando Histórico para o produto
        HistoricoProduto histProduto = new HistoricoProduto();
        histProduto.setNomeAnterior(produtoAntigo.getNome());
        histProduto.setDataAlteracao(LocalDate.now());
        histProduto.setQuantidadeAnterior(produtoAntigo.getQuantidadeEstoque());
        histProduto.setValorUnitarioAnterior(produtoAntigo.getPrecoUnitario());
        histProduto.setPrecoDeCustoAnterior(produtoAntigo.getPrecoCusto());

        //Criando relacionamento bidirecional
        histProduto.setProduto(produtoAntigo);
        produtoAntigo.getHistoricoProdutos().add(histProduto);

        //Método responsavel pela atualização dos dados
        atualizarDados(produtoAntigo, produtoAtualizado);

        //Persistindo alterações
        historicoProdutoRepository.save(histProduto);
        produtoRepository.save(produtoAntigo);
        GrowlView.showInfo(MessageEnum.MSG_SUCESSO.getMsg(), "Estoque adicionado.");
    }

    //Método para realizar atualizações de dados, onde produtoAntigo ficará com os dados atualizados de ProdutoAtualizado
    private void atualizarDados(Produto produtoAntigo, Produto produtoAtualizado) {
        produtoAntigo.setQuantidadeEstoque(produtoAntigo.getQuantidadeEstoque() + produtoAtualizado.getQuantidadeEstoque());
        produtoAntigo.setPrecoCusto(produtoAtualizado.getPrecoCusto());
        produtoAntigo.setDataAlteracao(LocalDate.now());
        produtoAntigo.setPrecoUnitario(produtoAtualizado.getPrecoUnitario());
    }

    public void exportarParaExcel() {
        if (produtoRepository.findAll().isEmpty()) {
            GrowlView.showError(MessageEnum.MSG_ERRO.getMsg(), "Não existem dados de produtos para exportar no momento!");
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

            try {
                // Gera o arquivo Excel
                ByteArrayOutputStream outputStream = excelExporter.exportarProdutoParaExcel(produtoRepository.findAll());

                // Configura os headers para download
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=\"relatorio_produtos.xls\"");
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
