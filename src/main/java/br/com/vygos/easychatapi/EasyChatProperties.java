package br.com.vygos.easychatapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("easychat")
public class EasyChatProperties {

    private Security security;
    private String origin;

    @Data
    public static class Security {
        private String clientId;
        private String clientSecret;
    }
}
