package br.edu.ifal.mpng.finview.aplicacao.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovaTransacaoCSV;
import br.edu.ifal.mpng.finview.dominio.transacao.Transacao;
import br.edu.ifal.mpng.finview.dominio.transacao.TransacaoRepository;
import br.edu.ifal.mpng.finview.infra.ParseadorDeCSV;


@Service
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public void processCSV(MultipartFile file) throws ParseException {
        try {
            List<RequisicaoNovaTransacaoCSV> requisicoes = ParseadorDeCSV.parse(file.getInputStream());

            List <Transacao> transacoes = new ArrayList<>();
            for (RequisicaoNovaTransacaoCSV requisicao : requisicoes) {
            	Transacao transacao = requisicao.toTransacao();
            	transacoes.add(transacao);
            }
            transacaoRepository.saveAll(transacoes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
