package study.trychat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import study.trychat.exception.custom.CustomDuplicateUsernameException;
import study.trychat.exception.custom.CustomPrimaryKeyMismatchException;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(CustomDuplicateUsernameException.class)
  public ResponseEntity<ApiError> handleCustomDuplicateUsernameException(CustomDuplicateUsernameException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(CustomPrimaryKeyMismatchException.class)
  public ResponseEntity<ApiError> primaryKeyMismatchException(CustomPrimaryKeyMismatchException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
