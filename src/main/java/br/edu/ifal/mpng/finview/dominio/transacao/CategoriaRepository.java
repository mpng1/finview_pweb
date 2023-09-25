package br.edu.ifal.mpng.finview.dominio.transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	Categoria findByNome(Categoria categoria);

	Categoria findByNome(String categoria);

	
}
