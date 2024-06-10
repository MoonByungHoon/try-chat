package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class DuplicateFriendByUserNameException extends RuntimeException {
  public DuplicateFriendByUserNameException() {
    super(ErrorMessage.FRIEND_ADD_DUPLICATE_USER_NAME.getMessage());
  }
}
