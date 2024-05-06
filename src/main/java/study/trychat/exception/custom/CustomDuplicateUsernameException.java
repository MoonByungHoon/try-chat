package study.trychat.exception.custom;

public class CustomDuplicateUsernameException extends RuntimeException {

  public CustomDuplicateUsernameException(String message) {
    super(message);
  }
}
