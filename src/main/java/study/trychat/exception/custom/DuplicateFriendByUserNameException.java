package study.trychat.exception.custom;

import study.trychat.message.ErrorMessage;

public class DuplicateFriendByUserNameException extends RuntimeException {
  public DuplicateFriendByUserNameException() {
    super(ErrorMessage.FRIEND_ADD_DUPLICATE_USER_NAME.getMessage());
  }
}
