package br.edu.ifal.mpng.finview.dominio.transacao;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name="Transacao")
@Table(name="transacao")
public class Transacao {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Date data;
	
	private BigDecimal valor;
	
	private String descricao;
	
	@ManyToOne
	private Categoria categoria;
	
	@Enumerated(EnumType.STRING)
	private SomaOuSubtrai somaOuSubtrai;
	

	public SomaOuSubtrai getSomaOuSubtrai() {
		return somaOuSubtrai;
	}

	public void setSomaOuSubtrai(SomaOuSubtrai somaOuSubtrai) {
		this.somaOuSubtrai = somaOuSubtrai;
	}

	public Transacao(Date data, BigDecimal valor, String descricao, Categoria categoria) {
	    this.data = data;
	    this.valor = valor;
	    this.descricao = descricao;
	    this.categoria = categoria;
	    if (valor.compareTo(BigDecimal.ZERO) > 0) {
	        this.somaOuSubtrai = SomaOuSubtrai.SOMA;
	    } else if (valor.compareTo(BigDecimal.ZERO) < 0) {
	        this.somaOuSubtrai = SomaOuSubtrai.SUBTRAI;
	    }
	}
	
	public Transacao() {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	

	
	
	

}
