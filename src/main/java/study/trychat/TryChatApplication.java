package study.trychat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TryChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(TryChatApplication.class, args);
	}

}
