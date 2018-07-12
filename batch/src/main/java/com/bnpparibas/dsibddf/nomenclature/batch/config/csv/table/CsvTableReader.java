package com.bnpparibas.dsibddf.nomenclature.batch.config.csv.table;

import com.opencsv.CSVReaderHeaderAware;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.item.ItemReader;

import java.io.FileReader;
import java.util.Map;

@Slf4j
class CsvTableReader implements ItemReader<Map<String, String>> {

    private final String fileName;

    CsvTableReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Map<String, String> read() throws Exception {
        LOGGER.info(String.format("start reading table & fields from csv file %s", fileName));
        val file = this.getClass().getClassLoader().getResource(fileName).getFile();
        val result = new CSVReaderHeaderAware(new FileReader(file)).readMap();
        if (result == null)
            LOGGER.info(String.format("The content read from csv files %s is empty, so all sub steps of this step will be skipped", fileName));
        return result;
    }
}
