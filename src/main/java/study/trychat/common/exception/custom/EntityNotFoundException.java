package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class EntityNotFoundException extends RuntimeException {
  public EntityNotFoundException() {
    super(ErrorMessage.MEMBER_ENTITY_NOT_FOUND.getMessage());
  }
}
