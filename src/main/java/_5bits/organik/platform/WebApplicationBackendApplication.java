package _5bits.organik.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WebApplicationBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplicationBackendApplication.class, args);
	}

}
