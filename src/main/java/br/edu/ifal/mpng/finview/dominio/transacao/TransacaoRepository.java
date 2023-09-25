package br.edu.ifal.mpng.finview.dominio.transacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

	List<Transacao> findBySomaOuSubtraiOrderByDataDescIdDesc(SomaOuSubtrai somaOuSubtrai);
	
	List<Transacao> findAllByOrderByDataDescIdDesc();

	List<Transacao> findByCategoria(Categoria categoria);
	

}
