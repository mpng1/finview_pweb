package br.edu.ifal.mpng.finview.aplicacao.dto;

import java.math.BigDecimal;
import java.text.ParseException;

import br.edu.ifal.mpng.finview.dominio.orcamento.Orcamento;
import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import jakarta.validation.constraints.NotNull;

public class RequisicaoEditarOrcamento {
	
	private Long id;
	
	@NotNull
	private Categoria categoria;
	
	private BigDecimal limite;
	



	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Categoria getCategoria() {
		return categoria;
	}




	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}




	public BigDecimal getLimite() {
		return limite;
	}




	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}




	public Orcamento toOrcamento() throws ParseException {
		Orcamento orcamento = new Orcamento();
		orcamento.setId(id);
	    orcamento.setCategoria(categoria);
	    orcamento.setLimite(limite);
		
	    return orcamento;
}
}
