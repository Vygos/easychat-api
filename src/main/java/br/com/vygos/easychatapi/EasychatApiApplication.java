package br.com.vygos.easychatapi;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@RequiredArgsConstructor
@EnableConfigurationProperties({EasyChatProperties.class})
@EnableWebSocketMessageBroker
@SpringBootApplication
public class EasychatApiApplication {

	private final EasyChatProperties easyChatProperties;

	public static void main(String[] args) {
		SpringApplication.run(EasychatApiApplication.class, args);
	}


	@Bean
	public AmazonS3 s3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(easyChatProperties.getAws().getAccessKeyId(),
				easyChatProperties.getAws().getSecretKey());

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();

		return s3Client;
	}

}
