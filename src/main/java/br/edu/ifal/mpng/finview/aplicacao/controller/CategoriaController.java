package br.edu.ifal.mpng.finview.aplicacao.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoEditarCategoria;
import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovaCategoria;
import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import br.edu.ifal.mpng.finview.dominio.transacao.CategoriaRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("categoria")
public class CategoriaController {
	
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public String nova(RequisicaoNovaCategoria requisicao) {
		return "categoria";
	}
	
    @GetMapping("/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
    	Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
	    RequisicaoEditarCategoria requisicaoEditarCategoria = new RequisicaoEditarCategoria();
	    requisicaoEditarCategoria.setNome(categoria.getNome());
	    model.addAttribute("id", categoria.getId());
	    model.addAttribute("requisicaoEditarCategoria", requisicaoEditarCategoria);

	    return "categoriaeditar";
    }
	
	@PostMapping
	public String enviar(@Valid RequisicaoNovaCategoria requisicao, BindingResult result) throws ParseException {
		if(result.hasErrors()) {
			return "categoria";
		}
		Categoria categoria = requisicao.toCategoria();
		categoriaRepository.save(categoria);
		return "redirect:/lista/categoria";

	}
	
    @PostMapping("/{id}")
    public String atualizarCategoria(
            @PathVariable Long id,
            @Valid RequisicaoNovaCategoria requisicao,
            BindingResult result
    ) throws ParseException {
        if (result.hasErrors()) {
            return "redirect:/categoria/" + id;
        }
        Categoria categoria = requisicao.toCategoria();
        categoria.setId(id);
        categoriaRepository.save(categoria);
        return "redirect:/lista/categoria";
    }
    
    @DeleteMapping("/{id}")
    public String deletarCategoria(@PathVariable Long id) {
        try {
            categoriaRepository.deleteById(id);
            return "redirect:/lista/categoria";
        } catch (DataIntegrityViolationException e) {
            return "errodeletecategoria";
        }
    }
}
	
	
	

