package com.cognizant.webflux.uploadmilionsproducts.config;

import com.cognizant.webflux.uploadmilionsproducts.dto.ProductDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class AppConfig {

    @Bean
    public Sinks.Many<ProductDTO> productSink() {
        return Sinks.many().replay().limit(1);
    }
}
