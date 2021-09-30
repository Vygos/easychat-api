package br.com.vygos.easychatapi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@RequiredArgsConstructor
@EnableConfigurationProperties({EasyChatProperties.class})
@EnableWebSocketMessageBroker
@SpringBootApplication
public class EasychatApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasychatApiApplication.class, args);
	}

}
