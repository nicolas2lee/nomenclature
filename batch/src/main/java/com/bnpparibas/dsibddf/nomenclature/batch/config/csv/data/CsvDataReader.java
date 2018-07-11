package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.data;

import com.opencsv.CSVReaderHeaderAware;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.item.ItemReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

@Slf4j
class CsvDataReader implements ItemReader<Map<String, String>> {
    private final String fileName;

    CsvDataReader(final String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<String, String> read() throws IOException {
        LOGGER.info(String.format("start reading table & fields from csv file %s", fileName));
        val file = this.getClass().getClassLoader().getResource(fileName).getFile();
        return new CSVReaderHeaderAware(new FileReader(file)).readMap();
    }
}
