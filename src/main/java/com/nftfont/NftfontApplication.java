package com.nftfont;


import com.nftfont.config.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableCaching
public class NftfontApplication {

	public static void main(String[] args) {
		SpringApplication.run(NftfontApplication.class, args);
	}

}
