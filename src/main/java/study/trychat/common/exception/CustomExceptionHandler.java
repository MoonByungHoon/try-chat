package study.trychat.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import study.trychat.common.exception.custom.BaseCustomExceptions;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(BaseCustomExceptions.class)
  public ResponseEntity<ErrorResponse> handleCustomExceptions(BaseCustomExceptions ex) {
    ErrorMessage errorMessage = ex.getErrorMessage();
    ErrorResponse errorResponse = new ErrorResponse(errorMessage.getMessage(), errorMessage.getStatus().value());
    return new ResponseEntity<>(errorResponse, errorMessage.getStatus());
  }
}
