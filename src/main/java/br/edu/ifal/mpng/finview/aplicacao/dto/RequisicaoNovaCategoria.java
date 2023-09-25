package br.edu.ifal.mpng.finview.aplicacao.dto;

import java.text.ParseException;

import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import jakarta.validation.constraints.NotNull;

public class RequisicaoNovaCategoria {
	
	
	@NotNull
	private String nome;
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public Categoria toCategoria() throws ParseException {
		Categoria categoria = new Categoria();
	    categoria.setNome(nome);
		
	    return categoria;
}
}
