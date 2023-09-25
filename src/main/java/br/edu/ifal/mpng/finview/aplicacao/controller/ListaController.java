package br.edu.ifal.mpng.finview.aplicacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import br.edu.ifal.mpng.finview.dominio.transacao.CategoriaRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.SomaOuSubtrai;
import br.edu.ifal.mpng.finview.dominio.transacao.Transacao;
import br.edu.ifal.mpng.finview.dominio.transacao.TransacaoRepository;

@Controller
@RequestMapping("lista")
public class ListaController {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public String lista(Model model) {
		List<Transacao> transacoes =  transacaoRepository.findAllByOrderByDataDescIdDesc();
		model.addAttribute("transacoes", transacoes);
		
		return "lista";
	}
	
	@GetMapping("/{somaOuSubtrai}")
	public String porSinal(@PathVariable("somaOuSubtrai") String somaOuSubtrai, Model model) {
		List<Transacao> transacoes =  transacaoRepository.findBySomaOuSubtraiOrderByDataDescIdDesc(SomaOuSubtrai.valueOf(somaOuSubtrai.toUpperCase()));
		model.addAttribute("transacoes", transacoes);
		model.addAttribute("somaOuSubtrai",somaOuSubtrai);
		return "lista";
	}
	
	@GetMapping("/categoria/{categoria}")
	public String porCategoria(@PathVariable("categoria") String categoria, Model model) {
		
		Categoria categoriaObj = categoriaRepository.findByNome(categoria);
		
		List<Transacao> transacoes =  transacaoRepository.findByCategoria(categoriaObj);
		model.addAttribute("transacoes", transacoes);
		model.addAttribute("categoria", categoria);
		return "lista";
	}
	
	@GetMapping("/categoria")
	public String listaCategoria(Model model) {
		List<Categoria> categoria =  categoriaRepository.findAll();
		model.addAttribute("categorias", categoria);
		
		return "listacategoria";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/lista";
	}
}
