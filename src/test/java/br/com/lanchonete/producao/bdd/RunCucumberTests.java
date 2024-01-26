package br.com.lanchonete.producao.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:build/reports/cucumber/cucumber-report.html"},
        features = "src/test/resources/features",
        glue = {"br.com.postech_lanchonete.pedido.bdd"})
public class RunCucumberTests {
}
