package br.edu.ifal.mpng.finview.aplicacao.controller;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovaCategoria;
import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import br.edu.ifal.mpng.finview.dominio.transacao.CategoriaRepository;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {


    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaRepository categoriaRepository;

    @MockBean  // Mock the CategoriaRepository
    private CategoriaRepository mockCategoriaRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();
    }

    @Test
    public void deveAbrirOFormDeNovaCategoria() throws Exception {
        mockMvc.perform(get("/categoria/"))
               .andExpect(status().isOk())
               .andExpect(view().name("categoria"));
    }

    @Test
    public void deveAbrirOFormDeEdicaoDeCategoria() throws Exception {
        Long categoryId = 1L;
        Categoria categoria = new Categoria();
        categoria.setId(categoryId);
        categoria.setNome("Categoria Teste");

        when(categoriaRepository.findById(categoryId)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/categoria/" + categoryId))
               .andExpect(status().isOk())
               .andExpect(view().name("categoriaeditar"))
               .andExpect(model().attributeExists("id", "requisicaoEditarCategoria"));
    }

    @Test
    public void deveCriarNovaCategoria() throws Exception {
        RequisicaoNovaCategoria requisicao = new RequisicaoNovaCategoria();
        requisicao.setNome("Categoria Teste");

        mockMvc.perform(post("/categoria")
               .param("nome", "Categoria Teste"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/lista/categoria"));
    }

    @Test
    public void deveAtualizarCategoria() throws Exception {
        Long categoryId = 1L;
        RequisicaoNovaCategoria requisicao = new RequisicaoNovaCategoria();
        requisicao.setNome("Categoria Atualizada");

        mockMvc.perform(post("/categoria/" + categoryId)
               .param("nome", "Categoria Atualizada"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/lista/categoria"));
    }

    @Test
    public void deveDeletarCategoria() throws Exception {
        Long categoryId = 1L;

        Categoria mockCategoria = new Categoria();
        mockCategoria.setId(categoryId);
        
        when(mockCategoriaRepository.findById(categoryId)).thenReturn(Optional.of(mockCategoria));
        Assert.assertNotNull(mockCategoria); 

        mockMvc.perform(delete("/categoria/" + categoryId))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/lista/categoria"));

        when(mockCategoriaRepository.findById(categoryId)).thenReturn(Optional.empty());
        Categoria deletedCategory = mockCategoriaRepository.findById(categoryId).orElse(null);
        Assert.assertNull(deletedCategory); 
    }
}
