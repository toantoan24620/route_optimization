package com.toanhv22.routeoptimization.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.TimeZone;

@Configuration
public class ObjectMapperConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper(com.fasterxml.jackson.databind.Module trimStringModule) {
        return new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setTimeZone(TimeZone.getDefault())
                .registerModule(new JavaTimeModule())
                .registerModule(trimStringModule);
    }

    @Bean
    public Module trimStringModule() {
        return new SimpleModule().addDeserializer(String.class, new StringWithoutSpaceDeserializer(String.class));
    }

    public static class StringWithoutSpaceDeserializer extends StdDeserializer<String> {

        protected StringWithoutSpaceDeserializer(Class<String> vc) {
            super(vc);
        }

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return StringUtils.trim(p.getText());
        }
    }
}
