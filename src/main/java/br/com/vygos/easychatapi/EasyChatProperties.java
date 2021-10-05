package br.com.vygos.easychatapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("easychat")
public class EasyChatProperties {

    private Security security;
    private String origin;
    private Aws aws = new Aws();

    @Data
    public static class Security {
        private String clientId;
        private String clientSecret;
    }

    @Data
    public static class Aws {
        private String accessKeyId;
        private String secretKey;
        private String bucketName;

    }
}
