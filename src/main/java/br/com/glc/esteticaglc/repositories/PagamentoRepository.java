package br.com.glc.esteticaglc.repositories;

import br.com.glc.esteticaglc.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
