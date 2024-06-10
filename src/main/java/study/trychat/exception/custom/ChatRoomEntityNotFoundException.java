package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class ChatRoomEntityNotFoundException extends RuntimeException {
  public ChatRoomEntityNotFoundException() {
    super(ErrorMessage.MEMBER_ENTITY_NOT_FOUND.getMessage());
  }
}
