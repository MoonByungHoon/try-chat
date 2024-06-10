package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.ChatBase.ChatMessageResponse;
import study.trychat.entity.ChatLog;
import study.trychat.entity.ChatMessage;
import study.trychat.entity.ChatRoom;
import study.trychat.exception.custom.ChatRoomEntityNotFoundException;
import study.trychat.repository.ChatRoomRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;

  @Transactional
  public ChatRoom createChatRoom(String name, Long userId, String nickname) {
    ChatRoom chatRoom = new ChatRoom(name, userId, nickname);

    return chatRoomRepository.save(chatRoom);
  }

  public List<ChatRoom> getRoomList(Long userId) {

    return chatRoomRepository.findByChatLogChatMembersOriginId(userId);
  }

  @Transactional
  public ChatRoom updateRoomName(Long roomId, String name) {

    ChatRoom room = findRoom(roomId);

    room.updateName(name);

    return room;
  }

  @Transactional
  public List<ChatRoom> removeRoom(Long roomId, Long userId) {
    chatRoomRepository.deleteById(roomId);

    return getRoomList(userId);
  }

  @Transactional
  public List<ChatRoom> outRoom(Long roomId, Long userId) {
    ChatRoom room = findRoom(roomId);

    room.out(userId);

    return getRoomList(userId);
  }

  public ChatMessageResponse saveMessage(Long roomId, Long originId, String message) {
    ChatRoom room = findRoom(roomId);

    ChatLog chatLog = room.getChatLog();

    chatLog.addMessage(new ChatMessage(originId, message));

    ChatMessageResponse.toChatLogEntity(room.getChatLog());

    return
  }

  private ChatRoom findRoom(Long roomId) {
    return chatRoomRepository.findById(roomId)
            .orElseThrow(ChatRoomEntityNotFoundException::new);
  }
}
