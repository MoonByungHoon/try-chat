package study.trychat.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
            .addServersItem(new Server().url("https://www.try-chat.co.kr"))
            .components(new Components())
            .info(apiInfo());
  }

  private Info apiInfo() {
    return new Info()
            .title("Try-Chat Api Test")
            .description("만약 문제가 생긴다면 필히 전화 주세요.")
            .version("1.0.0");
  }
}
