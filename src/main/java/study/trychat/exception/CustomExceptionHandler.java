package study.trychat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import study.trychat.exception.custom.CustomPrimaryKeyMismatchException;
import study.trychat.exception.custom.DuplicateUsernameException;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(DuplicateUsernameException.class)
  public ResponseEntity<ApiError> handleCustomDuplicateUsernameException(DuplicateUsernameException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ExceptionHandler(CustomPrimaryKeyMismatchException.class)
  public ResponseEntity<ApiError> primaryKeyMismatchException(CustomPrimaryKeyMismatchException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
