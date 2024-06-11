package study.trychat.common.exception.custom;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.trychat.common.exception.ErrorMessage;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseCustomExceptions extends RuntimeException {
  private final ErrorMessage errorMessage;
}
