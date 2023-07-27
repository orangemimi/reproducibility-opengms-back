package edu.njnu.opengms.r2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication (scanBasePackages = {"edu.njnu.opengms.common", "edu.njnu.opengms.r2"})
@ServletComponentScan
@EnableFeignClients
public class R2Application {

    public static void main(String[] args) {
        SpringApplication.run(R2Application.class, args);
    }

}
