package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public final class DuplicateFriendByUserNameException extends BaseCustomExceptions {
  public DuplicateFriendByUserNameException() {
    super(ErrorMessage.FRIEND_ADD_DUPLICATE_USER_NAME);
  }
}
