package study.trychat.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.chat.ChatBase.CreateChatRoomRequest;
import study.trychat.chat.ChatRoom;
import study.trychat.chat.service.ChatRoomService;

import java.util.List;

import static study.trychat.chat.ChatBase.JoinRoomRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRoomController {
  private final ChatRoomService chatRoomService;

  @PostMapping("/room")
  public ResponseEntity<ChatRoom> createChatRoom(CreateChatRoomRequest createChatRoomRequest, Long memberId, String nickname) {

    return ResponseEntity.ok(chatRoomService.createChatRoom(createChatRoomRequest.name(), memberId, nickname));
  }

  @PostMapping("/join/room")
  public ResponseEntity<ChatRoom> joinChatRoom(JoinRoomRequest joinRoomRequest) {

    return ResponseEntity.ok(chatRoomService.joinRoom(joinRoomRequest));
  }

  @GetMapping("/rooms")
  public ResponseEntity<List<ChatRoom>> getRoomList(Long memberId) {
    return ResponseEntity.ok(chatRoomService.getRoomList(memberId));
  }

  @PatchMapping("/room")
  public ResponseEntity<List<ChatRoom>> updateRoomName(Long roomId, String roomName, Long memberId) {

    return ResponseEntity.ok(chatRoomService.updateRoomName(roomId, roomName, memberId));
  }

  @PatchMapping("/room/nickname")
  public ResponseEntity<ChatRoom> updateNickname(Long roomId, Long memberId, String nickname) {

    return ResponseEntity.ok(chatRoomService.updateNickname(roomId, memberId, nickname));
  }

  @DeleteMapping("/room")
  public ResponseEntity<List<ChatRoom>> removeRoom(Long roomId, Long memberId) {

    return ResponseEntity.ok(chatRoomService.removeRoom(roomId, memberId));
  }

  @DeleteMapping("/out")
  public ResponseEntity<List<ChatRoom>> outRoom(Long roomId, Long memberId) {

    return ResponseEntity.ok(chatRoomService.outRoom(roomId, memberId));
  }
}
