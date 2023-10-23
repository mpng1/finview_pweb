package br.edu.ifal.mpng.finview.aplicacao.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoEditarOrcamento;
import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovoOrcamento;
import br.edu.ifal.mpng.finview.dominio.orcamento.Orcamento;
import br.edu.ifal.mpng.finview.dominio.orcamento.OrcamentoRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.CategoriaRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.TransacaoRepository;
import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

public class OrcamentoControllerTest {

    @Mock
    private OrcamentoRepository orcamentoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private OrcamentoController orcamentoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orcamentoController).build();
    }

    @Test
    public void deveAbrirFormularioDeCriarOrcamento() throws Exception {
        mockMvc.perform(get("/orcamento"))
                .andExpect(status().isOk())
                .andExpect(view().name("orcamento"))
                .andExpect(model().attributeExists("categorias"))
                .andExpect(model().attributeExists("requisicaoNovoOrcamento"));
    }

    @Test
    public void deveAbrirFormularioDeEditarOrcamento() throws Exception {
        Long orcamentoId = 1L;
        Orcamento mockOrcamento = new Orcamento();
        when(orcamentoRepository.findById(orcamentoId)).thenReturn(java.util.Optional.of(mockOrcamento));

        mockMvc.perform(get("/orcamento/{id}", orcamentoId))
                .andExpect(status().isOk())
                .andExpect(view().name("orcamentoeditar"))
                .andExpect(model().attribute("id", orcamentoId))
                .andExpect(model().attributeExists("categorias"))
                .andExpect(model().attributeExists("requisicaoEditarOrcamento"));
    }

    @Test
    public void deveCriarOrcamento() throws Exception {
        RequisicaoNovoOrcamento requisicao = new RequisicaoNovoOrcamento();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        mockMvc.perform(post("/orcamento")
                .flashAttr("requisicaoNovoOrcamento", requisicao)
                .param("limite", "1000")
                .param("categoria", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lista/orcamento"));
    }

    @Test
    public void deveAtualizarOrcamento() throws Exception {
        Long orcamentoId = 1L;
        RequisicaoNovoOrcamento requisicao = new RequisicaoNovoOrcamento();
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        mockMvc.perform(post("/orcamento/{id}", orcamentoId)
                .flashAttr("requisicaoNovoOrcamento", requisicao)
                .param("limite", "1000")
                .param("categoria", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orcamento/1"));
    }

    @Test
    public void deveExcluirOrcamento() throws Exception {
        Long orcamentoId = 1L;

        mockMvc.perform(delete("/orcamento/{id}", orcamentoId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lista/orcamento"));
    }
}
