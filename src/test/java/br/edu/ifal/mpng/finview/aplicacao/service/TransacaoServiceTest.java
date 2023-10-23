package br.edu.ifal.mpng.finview.aplicacao.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovaTransacaoCSV;
import br.edu.ifal.mpng.finview.dominio.transacao.Transacao;
import br.edu.ifal.mpng.finview.dominio.transacao.TransacaoRepository;
import br.edu.ifal.mpng.finview.infra.ParseadorDeCSV;




public class TransacaoServiceTest {


    @Mock
    private TransacaoRepository transacaoRepository;
    
    private TransacaoService transacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transacaoService = new TransacaoService(transacaoRepository);
    }

    @Test
    public void deveParsearCSVComSucesso() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/testFile.csv");

        List<RequisicaoNovaTransacaoCSV> requisicoesNovasTransacoes = ParseadorDeCSV.parse(inputStream);

        Assertions.assertEquals(1, requisicoesNovasTransacoes.size());

        RequisicaoNovaTransacaoCSV requisicaoNovaTransacao = requisicoesNovasTransacoes.get(0);
        Assertions.assertEquals("2023-10-04", requisicaoNovaTransacao.getData());
        BigDecimal expectedValue = new BigDecimal("-6.75");
        Assertions.assertEquals(expectedValue, requisicaoNovaTransacao.getValor());
        Assertions.assertEquals("Chesterfield Azul", requisicaoNovaTransacao.getDescricao());
    }



    @Test
    public void testExportToCsv() throws IOException {
        List<Transacao> transacoes = new ArrayList<>();

        String csvData = transacaoService.exportToCsv(transacoes);

        String[] lines = csvData.split(System.lineSeparator());

        String[] expectedCsvLines = {
            "\"Data\",\"Valor\",\"Descrição\",\"Categoria\"",
        };

        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
        }

        Assertions.assertLinesMatch(List.of(expectedCsvLines), List.of(lines));
    }


}

