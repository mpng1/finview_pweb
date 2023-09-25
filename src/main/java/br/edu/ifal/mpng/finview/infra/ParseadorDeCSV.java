package br.edu.ifal.mpng.finview.infra;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import br.edu.ifal.mpng.finview.aplicacao.dto.RequisicaoNovaTransacaoCSV;

public class ParseadorDeCSV {
	
    public static List<RequisicaoNovaTransacaoCSV> parse(InputStream inputStream) throws IOException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream, "UTF-8"))) {
           
            ColumnPositionMappingStrategy<RequisicaoNovaTransacaoCSV> mappingStrategy =
                    new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(RequisicaoNovaTransacaoCSV.class);

            
            CsvToBean<RequisicaoNovaTransacaoCSV> csvToBean = new CsvToBeanBuilder<RequisicaoNovaTransacaoCSV>(reader)
                    .withMappingStrategy(mappingStrategy)
                    .withType(RequisicaoNovaTransacaoCSV.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

      
            return csvToBean.parse();
         
        }
    }

}
    

