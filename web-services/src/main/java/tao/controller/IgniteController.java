package tao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tao.usecase.nomenclature.core.NomenclatureRepository;
import tao.usecase.nomenclature.core.repository.in.memory.apache.ignite.IgniteJDBC;

import java.sql.SQLException;

// TODO: 04/07/2018 for test only, should be removed when batch module completed
@RestController
@RequestMapping(value = "/api/v1/ignite")
public class IgniteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IgniteController.class);

    private IgniteJDBC igniteJDBC;
    private NomenclatureRepository nomenclatureRepository;

    IgniteController(IgniteJDBC igniteJDBC,
                     @Qualifier("nomenclatureRepository") NomenclatureRepository nomenclatureRepository){
        this.igniteJDBC = igniteJDBC;
        this.nomenclatureRepository = nomenclatureRepository;
    }

    @GetMapping(value = "/")
    String item (){
        try {
            igniteJDBC.createDatabaseTables("ignite_sql/create-db.sql");
            igniteJDBC.insertData("ignite_sql/insert-data.sql");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}

