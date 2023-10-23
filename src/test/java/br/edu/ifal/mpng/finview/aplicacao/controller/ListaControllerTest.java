package br.edu.ifal.mpng.finview.aplicacao.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.edu.ifal.mpng.finview.aplicacao.service.CategoriaService;
import br.edu.ifal.mpng.finview.dominio.orcamento.OrcamentoRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.CategoriaRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.SomaOuSubtrai;
import br.edu.ifal.mpng.finview.dominio.transacao.TransacaoRepository;

@WebMvcTest(ListaController.class)
public class ListaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransacaoRepository transacaoRepository;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @MockBean
    private OrcamentoRepository orcamentoRepository;

    @MockBean
    private CategoriaService categoriaService;

    @BeforeEach
    public void setup() {
    	
    }

    @Test
    public void deveAbrirAListaDeTransacoes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lista"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("lista"));
    }

    @Test
    public void deveAbrirAListaSegmentandoPorCategoria() throws Exception {
  
        mockMvc.perform(MockMvcRequestBuilders.get("/lista")
                .param("categoria", "SomeCategory"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("lista"));
    }

    @Test
    public void deveDistinguirReceitaDeDespesaNaView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/lista/someSomaOuSubtrai"))
               .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
               .andExpect(MockMvcResultMatchers.view().name("redirect:/lista"));
    }


    @Test
    public void deveAbrirAListaDeCategorias() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/lista/categoria"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("listacategoria"));
    }

    @Test
    public void deveAbrirAListaDeOrcamentos() throws Exception {
 
        mockMvc.perform(MockMvcRequestBuilders.get("/lista/orcamento"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("listaorcamento"));
    }
}
