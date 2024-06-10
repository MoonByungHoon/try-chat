package study.trychat.common.exception.custom;


import study.trychat.common.exception.ErrorMessage;

public class ChatRoomEntityNotFoundException extends RuntimeException {
  public ChatRoomEntityNotFoundException() {
    super(ErrorMessage.MEMBER_ENTITY_NOT_FOUND.getMessage());
  }
}
