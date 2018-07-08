package com.bnpparibas.dsibddf.nomenclature.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PersonItemProcessor implements ItemProcessor<Object, Object> {

    @Override
    public Object process(Object o) throws Exception {
        return null;
    }
}
