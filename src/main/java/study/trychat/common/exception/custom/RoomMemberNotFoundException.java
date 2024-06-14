package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class RoomMemberNotFoundException extends BaseCustomExceptions {
  public RoomMemberNotFoundException() {
    super(ErrorMessage.NOT_ROOM_MEMBER);
  }
}
