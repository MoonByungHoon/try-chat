package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class PrimaryKeyMismatchException extends RuntimeException {
  public PrimaryKeyMismatchException() {
    super(ErrorMessage.PRIMARY_KEY_MISMATCH.getMessage());
  }
}
