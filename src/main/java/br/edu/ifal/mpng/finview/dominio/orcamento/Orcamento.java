package br.edu.ifal.mpng.finview.dominio.orcamento;

import java.math.BigDecimal;

import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name="Orcamento")
@Table(name="orcamento")
public class Orcamento {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
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
	
	public Orcamento() {
		
	}


	public Orcamento(Long id, Categoria categoria, BigDecimal limite) {
		this.id = id;
		this.categoria = categoria;
		this.limite = limite;
	}
	
	
	
	
}
