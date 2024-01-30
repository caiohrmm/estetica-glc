package br.com.glc.esteticaglc.repositories;

import br.com.glc.esteticaglc.entities.Pagamento;
import br.com.glc.esteticaglc.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
