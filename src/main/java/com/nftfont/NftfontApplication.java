package com.nftfont;

import com.nftfont.core.configuration.properties.AppProperties;
import com.nftfont.core.configuration.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
public class NftfontApplication {

	public static void main(String[] args) {
		SpringApplication.run(NftfontApplication.class, args);
	}

}
