package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({@PropertySource(value = "classpath:application.properties")})
@SpringBootApplication
public class ApplicationEntry {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationEntry.class, args);
    }

}