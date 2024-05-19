package io.github.pudo58;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(
        @PropertySource("classpath:man_clothes.properties")
)
public class ManClothesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManClothesApplication.class, args);
    }
}
