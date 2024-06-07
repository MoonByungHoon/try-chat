package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class FriendNotFoundException extends RuntimeException {
  public FriendNotFoundException() {
    super(ErrorMessage.FRIEND_NO_SUCH.getMessage());
  }
}
