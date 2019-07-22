package com.mitrais.javabootcamp.charles.springbootrestapixmongodb;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/", glue= {"com.mitrais.javabootcamp.charles.springbootrestapixmongodb"}, plugin = {"pretty", "html:target/cucumber"})
public class SpringbootrestapixmongodbApplicationConfigurationTest{

}
