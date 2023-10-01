package br.edu.ifal.mpng.finview.aplicacao.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifal.mpng.finview.aplicacao.service.CategoriaService;
import br.edu.ifal.mpng.finview.dominio.orcamento.Orcamento;
import br.edu.ifal.mpng.finview.dominio.orcamento.OrcamentoRepository;
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
	
	@Autowired
	private OrcamentoRepository orcamentoRepository;
	
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public String lista(@RequestParam(name="categoria",required=false) String categoriaNome, Model model) {
		if (categoriaNome != null) {
			List<Categoria> categorias =  categoriaRepository.findAll();
			Categoria categoria = categoriaRepository.findByNome(categoriaNome);
			List<Transacao> transacoes = transacaoRepository.findByCategoriaOrderByDataDescIdDesc(categoria);
			model.addAttribute("categorias", categorias);
			model.addAttribute("transacoes", transacoes);
		} else {
		
		List<Transacao> transacoes =  transacaoRepository.findAllByOrderByDataDescIdDesc();
		List<Categoria> categorias =  categoriaRepository.findAll();

		model.addAttribute("categorias", categorias);
		model.addAttribute("transacoes", transacoes);
		}
		return "lista";
	}
	
	@GetMapping("/{somaOuSubtrai}")
	public String porSinal(@RequestParam(name="categoria",required=false) String categoriaNome, @PathVariable("somaOuSubtrai") String somaOuSubtrai, Model model) {
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
	
	@GetMapping("/orcamento")
	public String listaOrcamento(Model model) {
		List<Orcamento> orcamento = orcamentoRepository.findAll();
		Map<Categoria, BigDecimal> categoriaTotais = categoriaService.calcularTotalPorCategoria();
	    model.addAttribute("categoriaTotais", categoriaTotais);
		model.addAttribute("orcamentos", orcamento);
		
		return "listaorcamento";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/lista";
	}
}
