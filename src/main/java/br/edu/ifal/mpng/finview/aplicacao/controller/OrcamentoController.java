package br.edu.ifal.mpng.finview.aplicacao.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoEditarOrcamento;
import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovoOrcamento;
import br.edu.ifal.mpng.finview.dominio.orcamento.Orcamento;
import br.edu.ifal.mpng.finview.dominio.orcamento.OrcamentoRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import br.edu.ifal.mpng.finview.dominio.transacao.CategoriaRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.TransacaoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("orcamento")
public class OrcamentoController {

	@Autowired
	OrcamentoRepository orcamentoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	TransacaoRepository transacaoRepository;
	
	@GetMapping("")
	public String novo(Model model) {
		List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        
        model.addAttribute("requisicaoNovoOrcamento", new RequisicaoNovoOrcamento());		
		return "orcamento";
	}
	
	@GetMapping("/{id}")
	public String editarOrcamento(@PathVariable Long id, Model model) {
	    Orcamento orcamento = orcamentoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Orçamento não encontrado"));
	    RequisicaoEditarOrcamento requisicaoEditarOrcamento = new RequisicaoEditarOrcamento();
	    requisicaoEditarOrcamento.setCategoria(orcamento.getCategoria()); 
	    requisicaoEditarOrcamento.setLimite(orcamento.getLimite());
	    List<Categoria> categorias = categoriaRepository.findAll();
	    model.addAttribute("id", id);
	    model.addAttribute("categorias", categorias);
	    model.addAttribute("requisicaoEditarOrcamento", requisicaoEditarOrcamento);

	    return "orcamentoeditar";
	}
	
	@PostMapping("")
	public String enviar(@Valid RequisicaoNovoOrcamento requisicao, BindingResult result) throws ParseException {
		if(result.hasErrors()) {
			return "orcamento";
		}
		Orcamento orcamento = requisicao.toOrcamento();
		orcamentoRepository.save(orcamento);
		return "redirect:/lista/orcamento";

	}
	
    @PostMapping("/{id}")
    public String atualizarOrcamento(
            @PathVariable Long id,
            @Valid RequisicaoNovoOrcamento requisicao,
            BindingResult result
    ) throws ParseException {
        if (result.hasErrors()) {
            return "redirect:/orcamento/" + id;
        }
        Orcamento orcamento = requisicao.toOrcamento();
        orcamento.setId(id);
        orcamentoRepository.save(orcamento);
        return "redirect:/lista/orcamento";
    }
    
    @DeleteMapping("/{id}")
    public String deletarOrcamento(@PathVariable Long id) {
        orcamentoRepository.deleteById(id);
        return "redirect:/lista/orcamento";
    }
}
