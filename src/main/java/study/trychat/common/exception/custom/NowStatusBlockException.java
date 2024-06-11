package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class NowStatusBlockException extends BaseCustomExceptions {
  public NowStatusBlockException() {
    super(ErrorMessage.FRIEND_NOW_BLOCK);
  }
}
