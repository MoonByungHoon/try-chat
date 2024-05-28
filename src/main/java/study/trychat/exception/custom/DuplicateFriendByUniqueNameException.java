package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class DuplicateFriendByUniqueNameException extends RuntimeException {
  public DuplicateFriendByUniqueNameException() {
    super(ErrorMessage.FRIEND_ADD_DUPLICATE_UNIQUE_NAME.getMessage());
  }
}
