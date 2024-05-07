package study.trychat.exception.custom;

public class PrimaryKeyMismatchException extends RuntimeException {

  public PrimaryKeyMismatchException(String message) {
    super(message);
  }
}
