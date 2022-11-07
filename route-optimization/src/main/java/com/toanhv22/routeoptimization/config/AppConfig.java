package com.toanhv22.routeoptimization.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@Getter
@RequiredArgsConstructor
public class AppConfig {
    private final List<String> localeResolverLanguages = Arrays.asList("en", "vi");
    private final String defaultLanguage = "vi";

    @Value("${spring.application.name}")
    private String appName;

    @Value("${google.maps.apiKey}")
    private String apiKey;
}
