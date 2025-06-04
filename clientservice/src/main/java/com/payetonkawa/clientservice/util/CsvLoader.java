package com.payetonkawa.clientservice.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.payetonkawa.clientservice.model.Client;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.util.List;

public class CsvLoader {

    public static List<Client> loadClientsFromCsv() throws Exception {
        return new CsvToBeanBuilder<Client>(
                new InputStreamReader(new ClassPathResource("client.csv").getInputStream()))
                .withType(Client.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();
    }
}
