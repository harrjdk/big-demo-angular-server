package dev.hornetshell.bigdemoangular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class BigDemoAngularApplication {

  public static void main(String[] args) {
    SpringApplication.run(BigDemoAngularApplication.class, args);
  }
}
