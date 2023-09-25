package br.edu.ifal.mpng.finview.aplicacao.dto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import com.opencsv.bean.CsvBindByPosition;

import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import br.edu.ifal.mpng.finview.dominio.transacao.SomaOuSubtrai;
import br.edu.ifal.mpng.finview.dominio.transacao.Transacao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RequisicaoNovaTransacaoCSV {
	
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");	
	
	@NotBlank
	@CsvBindByPosition(position = 0)
	private String data;
	
	@NotNull
	@CsvBindByPosition(position = 1)
	private BigDecimal valor;
	
	@NotBlank
	@CsvBindByPosition(position = 2)
	private String descricao;
	@Enumerated(EnumType.STRING)
	private SomaOuSubtrai somaOuSubtrai;
	
	private Categoria categoria;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
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
		
        if (!DATE_PATTERN.matcher(data).matches()) {
            throw new ParseException("Data inválida! Deve ter o formato dd-MM-yyyy", 0);
        }
		
	    SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    java.util.Date parsedDate = inputDateFormat.parse(data);
	    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

	    transacao.setData(sqlDate);
	    
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Transação deve ter uma descrição.");
        }
	    
		transacao.setDescricao(descricao);
		
        if (valor == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo!");
        }
		
		transacao.setValor(valor);
	    if (valor.compareTo(BigDecimal.ZERO) > 0) {
	        transacao.setSomaOuSubtrai(SomaOuSubtrai.SOMA);
	    } else if (valor.compareTo(BigDecimal.ZERO) < 0) {
	        transacao.setSomaOuSubtrai(SomaOuSubtrai.SUBTRAI);
	    } else {
	    	throw new IllegalArgumentException("Valor não pode ser zero!");
		
	}
	    Categoria defaultCategoria = new Categoria((long) 3, "Indefinida");
	    transacao.setCategoria(defaultCategoria);
	    return transacao;
}
}
