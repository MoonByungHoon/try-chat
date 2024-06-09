package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class FindTargetMismatchException extends RuntimeException {
  public FindTargetMismatchException() {
    super(ErrorMessage.FIND_TARGET_MISMATCH.getMessage());
  }
}
