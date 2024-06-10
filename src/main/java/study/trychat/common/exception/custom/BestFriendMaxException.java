package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class BestFriendMaxException extends RuntimeException {
  public BestFriendMaxException() {
    super(ErrorMessage.FRIEND_BEST_SIZE_MAX.getMessage());
  }
}
