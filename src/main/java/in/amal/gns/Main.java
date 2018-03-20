package in.amal.gns;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@EnableEmailTools
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
