package io.github.pudo58.base.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.TimeZone.getTimeZone;

@Configuration
public class ResponseConfig {
    public static final String TIME_ZONE = "GMT+7";

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setTimeZone(getTimeZone(TIME_ZONE));
        return objectMapper;
    }
}
