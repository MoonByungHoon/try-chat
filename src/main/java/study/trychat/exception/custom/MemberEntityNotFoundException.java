package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class MemberEntityNotFoundException extends RuntimeException {
  public MemberEntityNotFoundException() {
    super(ErrorMessage.MEMBER_ENTITY_NOT_FOUND.getMessage());
  }
}
