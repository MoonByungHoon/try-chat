package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public final class NowStatusBestException extends BaseCustomExceptions {
  public NowStatusBestException() {
    super(ErrorMessage.FRIEND_NOW_BEST);
  }
}
