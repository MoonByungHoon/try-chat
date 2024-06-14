package study.trychat.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import study.trychat.chat.ChatBase.ChatMessageRequest;
import study.trychat.chat.RoomMember;
import study.trychat.chat.service.ChatRoomService;

//메세지 처리 컨트롤러
//Stomp는 메세지를 처리할때에 @Controller를 사용한다. @RestController는 사용하지 않는다.
//이유는 @RestController는 HTTP 요청을 처리하는 목적으로 사용되기 때문이다.
//Stomp는 WebSocket를 통해서 비동기적으로 메세지를 주고 받는다.
@Controller
@RequiredArgsConstructor
public class ChatMessageController {
  //  동적인 URL에 대응하기 위해서는 MessagingTemplate가 필요함.
  private final SimpMessagingTemplate smt;
  private final ChatRoomService chatRoomService;

  @MessageMapping("/chat.sendMessage")
  public void sendMessage(@Payload ChatMessageRequest chatMessageRequest) {
    chatRoomService.saveChatMessage(chatMessageRequest);
    smt.convertAndSend("/topic/" + chatMessageRequest.chatRoomId(), chatMessageRequest);
  }

  @MessageMapping("/chat.addUser")
  public void addUser(@Payload Long userId) {
    RoomMember roomMember = chatRoomService.getRoomMember(userId);
    String greeting = roomMember.getNickname() + "님이 입장하였습니다.";
    smt.convertAndSend("/topic/" + roomMember.getChatRoom().getId(), greeting);
  }
}
