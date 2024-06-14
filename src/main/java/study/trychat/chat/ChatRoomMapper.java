package study.trychat.chat;

public class ChatRoomMapper {

  public static ChatBase.ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom) {
    return new ChatRoomResponse(chatRoom.getId(), chatRoom.getName());
  }
}
