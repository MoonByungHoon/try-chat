package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class BestFriendMaxException extends RuntimeException {
  public BestFriendMaxException() {
    super(ErrorMessage.FRIEND_BEST_SIZE_MAX.getMessage());
  }
}
