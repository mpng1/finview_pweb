package br.edu.ifal.mpng.finview.dominio.transacao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

	List<Transacao> findBySomaOuSubtraiOrderByDataDescIdDesc(SomaOuSubtrai somaOuSubtrai);
	
	List<Transacao> findAllByOrderByDataDescIdDesc();

	List<Transacao> findByCategoria(Categoria categoria);

   @Query("SELECT SUM(t.valor) FROM Transacao t WHERE t.categoria = :categoria")
    BigDecimal calculateTotalByCategoria(@Param("categoria") Categoria categoria);

   List<Transacao> findBySomaOuSubtraiAndCategoriaOrderByDataDescIdDesc(SomaOuSubtrai somaOuSubtrai, Categoria categoria);

   List<Transacao> findByCategoriaOrderByDataDescIdDesc(Categoria categoria);
	

}
