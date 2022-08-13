package com.nftfont.core.configuration.properties;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Configuration
public class AppProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth {
        @Value("${app.auth.tokenSecret}")
        private String tokenSecret;
        @Value("${app.auth.tokenExpiry}")
        private long tokenExpiry;
        @Value("${app.auth.refreshTokenExpiry}")
        private long refreshTokenExpiry;
    }

    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

}

