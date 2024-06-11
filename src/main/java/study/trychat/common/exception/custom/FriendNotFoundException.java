package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public final class FriendNotFoundException extends BaseCustomExceptions {
  public FriendNotFoundException() {
    super(ErrorMessage.FRIEND_NO_SUCH);
  }
}
