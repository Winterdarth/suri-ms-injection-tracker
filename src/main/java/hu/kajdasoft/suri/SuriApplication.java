package hu.kajdasoft.suri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan({"hu.kajdasoft.config", "hu.kajdasoft.suri"})
public class SuriApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuriApplication.class, args);
    }

}
