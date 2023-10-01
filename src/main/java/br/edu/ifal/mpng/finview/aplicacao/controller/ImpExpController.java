package br.edu.ifal.mpng.finview.aplicacao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovaCategoria;

@Controller
@RequestMapping("impexp")
public class ImpExpController {
	
	@GetMapping
	public String chamarPagina() {
		return "importexport";
	}

}
