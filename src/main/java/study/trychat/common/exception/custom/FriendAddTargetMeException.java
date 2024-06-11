package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public final class FriendAddTargetMeException extends BaseCustomExceptions {
  public FriendAddTargetMeException() {
    super(ErrorMessage.FRIEND_ADD_TARGET_ME);
  }
}
