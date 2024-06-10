//package study.trychat.chat.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import study.trychat.chat.ChatBase.ChatMessageRequest;
//import study.trychat.chat.ChatBase.CreateChatRoomRequest;
//import study.trychat.chat.ChatRoom;
//import study.trychat.chat.service.ChatRoomService;
//
//import java.util.List;
//
////메세지 처리 컨트롤러
////Stomp는 메세지를 처리할때에 @Controller를 사용한다. @RestController는 사용하지 않는다.
////이유는 @RestController는 HTTP 요청을 처리하는 목적으로 사용되기 때문이다.
////Stomp는 WebSocket를 통해서 비동기적으로 메세지를 주고 받는다.
//@Controller
//@RequiredArgsConstructor
//public class ChatRoomController {
//  //  동적인 URL에 대응하기 위해서는 MessagingTemplate가 필요함.
//  private final SimpMessagingTemplate smt;
//  private final ChatRoomService chatRoomService;
//
//  @MessageMapping("/chat/createChatRoom")
////  정적인 URL에 단순하게 대응하기 위해서는 @SendTo 어노테이션을 활용하면 편함.
//  @SendTo("/topic/chat/chat-rooms")
//  public ChatRoom createChatRoom(CreateChatRoomRequest createChatRoomRequest, Long userId, String nickname) {
//
//    return chatRoomService.createChatRoom(createChatRoomRequest.name(), userId, nickname);
//  }
//
//  @MessageMapping("/chat/getChatRooms")
//  @SendTo("/topic/chat/chat-rooms")
//  public List<ChatRoom> getRoomList(Long userId) {
//    return chatRoomService.getRoomList(userId);
//  }
//
//  @MessageMapping("/chat/updateRoomName")
//  @SendTo("/topic/chat/chat-rooms")
//  public ChatRoom updateRoomName(Long roomId, String name) {
//
//    return chatRoomService.updateRoomName(roomId, name);
//  }
//
//  @MessageMapping("/chat/deleteRoomName")
//  @SendTo("/topic/chat/chat-rooms")
//  public List<ChatRoom> removeRoom(Long roomId, Long userId) {
//
//    return chatRoomService.removeRoom(roomId, userId);
//  }
//
//  @MessageMapping("/chat/outRoom")
//  @SendTo("/topic/chat/chat-rooms")
//  public List<ChatRoom> outRoom(Long roomId, Long userId) {
//
//    return chatRoomService.outRoom(roomId, userId);
//  }
//
//  @MessageMapping("/chat/{roomId}/sendMessage")
//  public void sendMessage(@DestinationVariable Long roomId, ChatMessageRequest chatMessageRequest) {
//    chatRoomService.saveMessage(roomId, chatMessageRequest.userId(), chatMessageRequest.message());
//
//    smt.convertAndSend("/topic/" + roomId, );
//  }
//}
