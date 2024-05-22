package com.syncrosa.livestreamingservice;

import com.syncrosa.livestreamingservice.config.NginxConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties({
		NginxConfig.class
})
@EnableJpaAuditing
public class LiveStreamingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiveStreamingServiceApplication.class, args);
	}

}
