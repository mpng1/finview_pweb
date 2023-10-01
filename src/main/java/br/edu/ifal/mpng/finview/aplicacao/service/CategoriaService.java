package br.edu.ifal.mpng.finview.aplicacao.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import br.edu.ifal.mpng.finview.dominio.transacao.CategoriaRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.TransacaoRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository; 
	
	@Autowired
	private TransacaoRepository transacaoRepository; 
	
	public Map<Categoria, BigDecimal> calcularTotalPorCategoria() {
		List<Categoria> categorias = categoriaRepository.findAll();
		Map<Categoria, BigDecimal> categoriaTotais = new HashMap<>();
		
		for (Categoria categoria : categorias) {
			BigDecimal total = transacaoRepository.calculateTotalByCategoria(categoria);
			categoriaTotais.put(categoria, total);
		}
		
		return categoriaTotais;
				
	}

}
