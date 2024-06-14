package study.trychat.common.exception.custom;


import study.trychat.common.exception.ErrorMessage;

public class ChatRoomEntityNotFoundException extends BaseCustomExceptions {
  public ChatRoomEntityNotFoundException() {
    super(ErrorMessage.CHATROOM_ENTITY_NOT_FOUND);
  }
}
