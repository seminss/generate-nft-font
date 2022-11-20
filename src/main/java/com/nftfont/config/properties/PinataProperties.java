package com.nftfont.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PinataProperties {
    @Value("${pinata.key}")
    private String key;
    @Value("${pinata.apikey}")
    private String apiKey;
}
