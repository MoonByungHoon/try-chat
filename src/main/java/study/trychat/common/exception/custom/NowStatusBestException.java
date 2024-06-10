package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class NowStatusBestException extends RuntimeException {
  public NowStatusBestException() {
    super(ErrorMessage.FRIEND_NOW_BEST.getMessage());
  }
}
