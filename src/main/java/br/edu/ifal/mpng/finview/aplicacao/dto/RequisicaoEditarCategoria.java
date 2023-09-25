package br.edu.ifal.mpng.finview.aplicacao.dto;

import java.text.ParseException;

import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import jakarta.validation.constraints.NotNull;

public class RequisicaoEditarCategoria {
	
	private Long id;
	
	@NotNull
	private String nome;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public Categoria toCategoria() throws ParseException {
		Categoria categoria = new Categoria();
		categoria.setId(id);
	    categoria.setNome(nome);
		
	    return categoria;
}
}
