package br.edu.ifal.mpng.finview.dominio.orcamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
	
	List<Orcamento> findByCategoria(Categoria categoria);

}
