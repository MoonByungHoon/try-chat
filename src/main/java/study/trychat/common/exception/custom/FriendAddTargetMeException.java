package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class FriendAddTargetMeException extends RuntimeException {
  public FriendAddTargetMeException() {
    super(ErrorMessage.FRIEND_ADD_TARGET_ME.getMessage());
  }
}
