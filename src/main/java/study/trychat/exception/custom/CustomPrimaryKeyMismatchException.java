package study.trychat.exception.custom;

public class CustomPrimaryKeyMismatchException extends RuntimeException {

  public CustomPrimaryKeyMismatchException(String message) {
    super(message);
  }
}
