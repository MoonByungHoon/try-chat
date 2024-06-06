package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class EntityNotFoundException extends RuntimeException {
  public EntityNotFoundException() {
    super(ErrorMessage.MEMBER_ENTITY_NOT_FOUND.getMessage());
  }
}
