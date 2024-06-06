package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class FindTargetMismatchException extends RuntimeException {
  public FindTargetMismatchException() {
    super(ErrorMessage.FIND_TARGET_MISMATCH.getMessage());
  }
}
