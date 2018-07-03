package tao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tao.config.IgniteJDBC;

import java.sql.SQLException;

@RestController
@RequestMapping(value = "/api/v1/ignite")
public class IgniteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IgniteController.class);

    private IgniteJDBC igniteJDBC;
    IgniteController(IgniteJDBC igniteJDBC){
        this.igniteJDBC = igniteJDBC;
    }

    @GetMapping(value = "/")
    String item (){
        try {
            igniteJDBC.main();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}

