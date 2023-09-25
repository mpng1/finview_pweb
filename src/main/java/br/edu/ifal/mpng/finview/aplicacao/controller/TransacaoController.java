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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoEditarTransacao;
import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovaTransacao;
import br.edu.ifal.mpng.finview.aplicacao.service.TransacaoService;
import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import br.edu.ifal.mpng.finview.dominio.transacao.CategoriaRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.Transacao;
import br.edu.ifal.mpng.finview.dominio.transacao.TransacaoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("transacao")
public class TransacaoController {
	
	
	@Autowired
	private TransacaoService transacaoService;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@GetMapping
	public String nova(Model model) {
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("requisicaoNovaTransacao", new RequisicaoNovaTransacao());		
		return "transacao";
	}
	
	@GetMapping("/{id}")
	public String editarTransacao(@PathVariable Long id, Model model) {
	    Transacao transacao = transacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transacao não encontrada"));
	    RequisicaoEditarTransacao requisicaoEditarTransacao = new RequisicaoEditarTransacao();
	    requisicaoEditarTransacao.setData(transacao.getData()); 
	    requisicaoEditarTransacao.setValor(transacao.getValor()); 
	    requisicaoEditarTransacao.setDescricao(transacao.getDescricao()); 
	    requisicaoEditarTransacao.setCategoria(transacao.getCategoria());
	    List<Categoria> categorias = categoriaRepository.findAll();
	    model.addAttribute("id", transacao.getId());
	    model.addAttribute("categorias", categorias);
	    model.addAttribute("requisicaoEditarTransacao", requisicaoEditarTransacao);

	    return "transacaoeditar";
	}

	
	@PostMapping
	public String enviar(@Valid RequisicaoNovaTransacao requisicao, BindingResult result) throws ParseException {
		if(result.hasErrors()) {
			return "transacao";
		}
		Transacao transacao = requisicao.toTransacao();
		transacaoRepository.save(transacao);
		return "redirect:/lista/lista";

	}
	
	@PostMapping("/csv")
	public String importarCSV(@RequestParam("file") MultipartFile file) {
		try {
			transacaoService.processCSV(file);
			return "redirect:/lista/lista";
		} catch (Exception e) {
			return "Erro processando CSV";
		}
	}
	
    @PostMapping("/{id}")
    public String atualizarTransacao(
            @PathVariable Long id,
            @Valid RequisicaoEditarTransacao requisicao,
            BindingResult result
    ) throws ParseException {
        if (result.hasErrors()) {
            return "redirect:/transacao/" + id;
        }
        Transacao transacao = requisicao.toTransacao();
        transacao.setId(id);
        transacaoRepository.save(transacao);
        return "redirect:/lista/lista";
    }
    
    @DeleteMapping("/{id}")
    public String deletarTransacao(@PathVariable Long id) {
        transacaoRepository.deleteById(id);
        return "redirect:/lista/lista";
    }
	
	
	
	
}
