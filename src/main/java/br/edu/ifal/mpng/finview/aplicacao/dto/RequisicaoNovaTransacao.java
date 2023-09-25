package br.edu.ifal.mpng.finview.aplicacao.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;

import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import br.edu.ifal.mpng.finview.dominio.transacao.SomaOuSubtrai;
import br.edu.ifal.mpng.finview.dominio.transacao.Transacao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequisicaoNovaTransacao {
	
	@NotNull
	private Date data;
	
	@NotNull
	private BigDecimal valor;
	
	@NotBlank
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private SomaOuSubtrai somaOuSubtrai;
	
	private Categoria categoria;	
	
	
	public RequisicaoNovaTransacao(Transacao transacao) {
		// TODO Auto-generated constructor stub
	}
	public RequisicaoNovaTransacao() {
		// TODO Auto-generated constructor stub
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
	    this.data = data;
	}
	public SomaOuSubtrai getSomaOuSubtrai() {
		return somaOuSubtrai;
	}
	public void setSomaOuSubtrai(SomaOuSubtrai somaOuSubtrai) {
		this.somaOuSubtrai = somaOuSubtrai;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public Transacao toTransacao() throws ParseException {
		Transacao transacao = new Transacao();
	    transacao.setData(data);
		transacao.setDescricao(descricao);
		transacao.setValor(valor);
		transacao.setCategoria(categoria);
	    if (valor.compareTo(BigDecimal.ZERO) > 0) {
	        transacao.setSomaOuSubtrai(SomaOuSubtrai.SOMA);
	    } if (valor.compareTo(BigDecimal.ZERO) < 0) {
	        transacao.setSomaOuSubtrai(SomaOuSubtrai.SUBTRAI);
		
	}
	    return transacao;
}
}
