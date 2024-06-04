package study.trychat.exception.custom;

import study.trychat.message.ErrorMessage;

public class DuplicateUsernameException extends RuntimeException {
  public DuplicateUsernameException() {
    super(ErrorMessage.SIGN_UP_DUPLICATE_USER.getMessage());
  }
}
