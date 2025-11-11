package com.arka.sales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SalesConfig {
    @Bean
    RestTemplate restTemplate() {
        var factory = new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(480000);//480000 = 8 minutos
        factory.setReadTimeout(480000);

        return new RestTemplate(factory);

    }
}
