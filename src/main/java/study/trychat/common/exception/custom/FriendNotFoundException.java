package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class FriendNotFoundException extends RuntimeException {
  public FriendNotFoundException() {
    super(ErrorMessage.FRIEND_NO_SUCH.getMessage());
  }
}
