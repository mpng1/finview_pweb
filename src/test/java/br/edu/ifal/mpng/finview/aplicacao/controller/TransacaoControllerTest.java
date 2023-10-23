package br.edu.ifal.mpng.finview.aplicacao.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoEditarTransacao;
import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovaTransacao;
import br.edu.ifal.mpng.finview.aplicacao.service.TransacaoService;
import br.edu.ifal.mpng.finview.dominio.transacao.Categoria;
import br.edu.ifal.mpng.finview.dominio.transacao.CategoriaRepository;
import br.edu.ifal.mpng.finview.dominio.transacao.Transacao;
import br.edu.ifal.mpng.finview.dominio.transacao.TransacaoRepository;

public class TransacaoControllerTest {

    private TransacaoController transacaoController;

    @Mock
    private TransacaoService transacaoService;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @BeforeEach
    public void setUp() {
        categoriaRepository = mock(CategoriaRepository.class);
        transacaoRepository = mock(TransacaoRepository.class);
        transacaoService = mock(TransacaoService.class);

        transacaoController = new TransacaoController(transacaoService, categoriaRepository, transacaoRepository);
    }

    @Test
    public void testNova() {
        Model model = mock(Model.class);

        when(categoriaRepository.findAll()).thenReturn(new ArrayList<>());

        String viewName = transacaoController.nova(model);

        assertEquals("transacao", viewName);
        verify(model).addAttribute("categorias", new ArrayList<>());
        verify(model).addAttribute("requisicaoNovaTransacao", new RequisicaoNovaTransacao());
    }

    @Test
    public void testEditarTransacao() {
        Model model = mock(Model.class);
        Long id = 1L;
        
        // Create a RequisicaoEditarTransacao instance that matches the controller's behavior
        RequisicaoEditarTransacao expectedRequisicao = new RequisicaoEditarTransacao();
        expectedRequisicao.setData(new Date(2023-01-01));
        expectedRequisicao.setValor(new BigDecimal("100.00"));
        expectedRequisicao.setDescricao("Sample description");
        expectedRequisicao.setCategoria(new Categoria());

        when(transacaoRepository.findById(ArgumentMatchers.eq(id))).thenReturn(Optional.of(new Transacao()));
        
        String viewName = transacaoController.editarTransacao(id, model);

        assertEquals("transacaoeditar", viewName);
        
        verify(model).addAttribute(ArgumentMatchers.eq("1L"), ArgumentMatchers.eq(id));;
        verify(model).addAttribute(ArgumentMatchers.eq("100.00"), ArgumentMatchers.eq(expectedRequisicao.getValor()));
    }
    
    @Test
    public void testEnviarWithErrors() throws ParseException {
        RequisicaoNovaTransacao requisicao = new RequisicaoNovaTransacao();
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = transacaoController.enviar(requisicao, result);

        assertEquals("transacao", viewName);
    }

    @Test
    public void testEnviarWithoutErrors() throws ParseException {
        RequisicaoNovaTransacao requisicao = new RequisicaoNovaTransacao();
        requisicao.setValor(new BigDecimal("100.00"));
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        String viewName = transacaoController.enviar(requisicao, result);

        assertEquals("redirect:/lista/lista", viewName);
    }

    @Test
    public void testImportarCSV() throws ParseException {
        MultipartFile file = new MockMultipartFile("test.csv", "test.csv", "text/csv", "CSV data".getBytes());
        
        doNothing().when(transacaoService).processCSV(file);

        String viewName = transacaoController.importarCSV(file);

        assertEquals("redirect:/lista/lista", viewName);
    }

    @Test
    public void testImportarCSVError() throws ParseException {
        MultipartFile file = new MockMultipartFile("test.csv", "test.csv", "text/csv", "CSV data".getBytes());
        
        doNothing().when(transacaoService).processCSV(file);

        String viewName = transacaoController.importarCSV(file);

        assertEquals("Erro processando CSV", viewName);
    }

    @Test
    public void testExportarCSV() throws IOException {
        List<Transacao> transacoes = new ArrayList<>();

        when(transacaoRepository.findAll()).thenReturn(transacoes);
        when(transacaoService.exportToCsv(transacoes)).thenReturn("CSV data");

        ResponseEntity<byte[]> response = transacaoController.exportarCSV();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("application/octet-stream", response.getHeaders().getContentType().toString());
        assertEquals("form-data; name=\"attachment\"; filename=\"transactions.csv\"", response.getHeaders().getFirst("Content-Disposition"));
        assertEquals("CSV data", new String(response.getBody()));
    }

    @Test
    public void testExportarCSVError() throws IOException {
        when(transacaoRepository.findAll()).thenReturn(new ArrayList<>());
        when(transacaoService.exportToCsv(anyList())).thenThrow(new IOException("Error exporting CSV"));

        ResponseEntity<byte[]> response = transacaoController.exportarCSV();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro exportando CSV", new String(response.getBody()));
    }
}

