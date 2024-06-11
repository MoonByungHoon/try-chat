package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public final class BestFriendMaxException extends BaseCustomExceptions {
  public BestFriendMaxException() {
    super(ErrorMessage.FRIEND_BEST_SIZE_MAX);
  }
}
