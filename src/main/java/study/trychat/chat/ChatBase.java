package study.trychat.chat;

import static study.trychat.chat.ChatBase.*;

public sealed interface ChatBase permits CreateChatRoomRequest,
        ChatMessageRequest, ChatMessageResponse, JoinRoomRequest {
  //  이름이 포함된 메세지를 모델링 하기 위해서 만들어진 객체

  record CreateChatRoomRequest(
          String name
  ) implements ChatBase {
  }

  record JoinRoomRequest(
          Long roomId,
          Long memberId,
          String nickname
  ) implements ChatBase {
  }

  record ChatMessageRequest(
          Long chatRoomId,
          Long writerId,
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
//      new ChatMessageResponse(chatLog.getChatMessages());
    }
  }
}
