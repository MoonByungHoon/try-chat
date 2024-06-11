package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class DuplicateUsernameException extends BaseCustomExceptions {
  public DuplicateUsernameException() {
    super(ErrorMessage.SIGN_UP_DUPLICATE_USER);
  }
}
