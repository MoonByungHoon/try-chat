package study.trychat.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiError {
  @Schema(description = "에러 코드", example = "500")
  private HttpStatus status;
  @Schema(description = "에러 메세지", example = "Internal Server Error")
  private String message;
}
