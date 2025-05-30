package com.cognizant.webflux.rest;

import com.cognizant.webflux.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;


@RestController
@RequestMapping("/traditional")
public class TraditionalRestController {
    private static final Logger logger = LoggerFactory.getLogger(TraditionalRestController.class);

    // this is the new Rest template
    private final RestClient restClient = RestClient.builder()
            .requestFactory(new JdkClientHttpRequestFactory())
            .baseUrl("http://localhost:7070")
            .build();

    @GetMapping("products")
    public List<ProductDTO> getProducts() {
        var list = this.restClient.get()
                .uri("/demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ProductDTO>>() {

                });

        logger.info("Traditional Received response " + list);

        return list;
    }

}
