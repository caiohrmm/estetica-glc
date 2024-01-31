package br.com.glc.esteticaglc.services;

import br.com.glc.esteticaglc.entities.HistoricoProduto;
import br.com.glc.esteticaglc.entities.Produto;
import br.com.glc.esteticaglc.entities.Usuario;
import br.com.glc.esteticaglc.repositories.HistoricoProdutoRepository;
import br.com.glc.esteticaglc.repositories.ProdutoRepository;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void excluir(Long codigoProduto) {
        Produto produto = produtoRepository.findById(codigoProduto).get();
        produtoRepository.delete(produto);
        Messages.addFlashGlobalInfo("Produto excluido com sucesso");
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }


    public Produto inserir(Long codigoUsuario, Produto novoProduto) {
        if (produtoRepository.findByNome(novoProduto.getNome()) == null) {
            novoProduto.setDataCriacao(LocalDate.now());

            //Buscando Usuário que registrou produto
            Usuario usuario = usuarioService.buscaPorCodigo(codigoUsuario);

            //Criando relacionamentos bidirecional para as entidades envolvidas
            novoProduto.setUsuario(usuario);
            usuario.getProdutos().add(novoProduto);

            //Persistindo alterações no banco de dados
            usuarioService.salvar(usuario);
            Messages.addFlashGlobalInfo("Produto registrado com sucesso");
            return produtoRepository.save(novoProduto);
        } else {
            Messages.addFlashGlobalError("Produto já cadastrado com o nome " + novoProduto.getNome());
            return null;
        }

    }


    public Produto atualizar(Long codigoProdutoAntigo, Produto produtoAtualizado) {
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
        return produtoRepository.save(produtoAntigo);
    }

    //Método para realizar atualizações de dados, onde produtoAntigo ficará com os dados atualizados de ProdutoAtualizado
    private void atualizarDados(Produto produtoAntigo, Produto produtoAtualizado) {
        produtoAntigo.setQuantidadeEstoque(produtoAntigo.getQuantidadeEstoque() + produtoAtualizado.getQuantidadeEstoque());
        produtoAntigo.setQuantidadeMinima(produtoAtualizado.getQuantidadeMinima());
        produtoAntigo.setPrecoCusto(produtoAtualizado.getPrecoCusto());
        produtoAntigo.setDataAlteracao(produtoAtualizado.getDataAlteracao());
        produtoAntigo.setPrecoUnitario(produtoAtualizado.getPrecoUnitario());
        produtoAntigo.setNome(produtoAtualizado.getNome());
        produtoAntigo.setDescricao(produtoAtualizado.getDescricao());
    }

}
