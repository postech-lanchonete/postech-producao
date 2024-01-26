package br.com.lanchonete.producao.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
@SuppressWarnings("unused")
@CucumberContextConfiguration
@SpringBootTest(classes = SpringBootApplication.class)
public class CucumberSpringConfiguration {

}
