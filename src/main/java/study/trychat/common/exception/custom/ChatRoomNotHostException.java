package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class ChatRoomNotHostException extends BaseCustomExceptions {
  public ChatRoomNotHostException() {
    super(ErrorMessage.CHATROOM_NOT_HOST);
  }
}
