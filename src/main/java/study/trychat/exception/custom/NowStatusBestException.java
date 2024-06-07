package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class NowStatusBestException extends RuntimeException {
  public NowStatusBestException() {
    super(ErrorMessage.FRIEND_NOW_BEST.getMessage());
  }
}
