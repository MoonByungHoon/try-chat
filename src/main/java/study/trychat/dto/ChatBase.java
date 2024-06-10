package study.trychat.dto;

import study.trychat.entity.ChatLog;
import study.trychat.entity.ChatRoom;

import static study.trychat.dto.ChatBase.*;

public sealed interface ChatBase permits HelloMessage, CreateChatRoomRequest,
        ChatMessageRequest, ChatMessageResponse {

  record HelloMessage(
          String message,
          String nickname,
          String memberId,
          String profileImg,
          String backgroundImg,
          String profileImgPath
  ) implements ChatBase {
  }
  //  이름이 포함된 메세지를 모델링 하기 위해서 만들어진 객체

  record CreateChatRoomRequest(
          String name
  ) implements ChatBase {
  }

  record ChatMessageRequest(
          Long userId,
          String message
  ) implements ChatBase {
  }

  record ChatMessageResponse(
          Long userId,
          String content,
          String profileImg,
          String profileImgPath
  ) implements ChatBase {
    public static void toChatLogEntity(ChatLog chatLog) {
      new ChatMessageResponse(chatLog.getChatMessages())
    }
  }
}
