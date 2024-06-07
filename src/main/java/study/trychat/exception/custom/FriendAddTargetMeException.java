package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class FriendAddTargetMeException extends RuntimeException {
  public FriendAddTargetMeException() {
    super(ErrorMessage.FRIEND_ADD_TARGET_ME.getMessage());
  }
}
