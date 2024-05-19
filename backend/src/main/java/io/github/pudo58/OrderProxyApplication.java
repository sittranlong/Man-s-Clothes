package io.github.pudo58;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(
        @PropertySource("classpath:order_proxy.properties")
)
public class OrderProxyApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderProxyApplication.class, args);
    }
}
