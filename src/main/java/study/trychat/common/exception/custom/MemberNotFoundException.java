package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class MemberNotFoundException extends BaseCustomExceptions {
  public MemberNotFoundException() {
    super(ErrorMessage.MEMBER_ENTITY_NOT_FOUND);
  }
}
