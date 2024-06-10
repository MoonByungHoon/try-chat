package study.trychat.dto.convert;

import study.trychat.dto.ChatBase.ChatRoomResponse;
import study.trychat.entity.ChatRoom;

public class ChatRoomMapper {

  public static ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom) {
    return new ChatRoomResponse(chatRoom.getId(), chatRoom.getName());
  }
}
