package study.trychat.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.chat.ChatBase.ChatMessageRequest;
import study.trychat.chat.ChatBase.JoinRoomRequest;
import study.trychat.chat.ChatRoom;
import study.trychat.chat.ChatRoomRepository;
import study.trychat.chat.RoomMember;
import study.trychat.chat.RoomMemberRepository;
import study.trychat.common.exception.custom.ChatRoomEntityNotFoundException;
import study.trychat.common.exception.custom.ChatRoomNotHostException;
import study.trychat.common.exception.custom.RoomMemberNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;
  private final RoomMemberRepository roomMemberRepository;

  @Transactional
  public ChatRoom createChatRoom(String roomName, Long userId, String nickname) {
    ChatRoom chatRoom = ChatRoom.init(roomName, userId, nickname);

    return chatRoomRepository.save(chatRoom);
  }

  public List<ChatRoom> getRoomList(Long userId) {

    return chatRoomRepository.findByRoomMembersMemberId(userId);
  }

  @Transactional
  public List<ChatRoom> updateRoomName(Long roomId, String name, Long userId) {

    ChatRoom room = findRoom(roomId);

    validateCheckHost(room, userId);

    room.updateName(name);

    return getRoomList(userId);
  }

  private void validateCheckHost(ChatRoom room, Long userId) {
    boolean checkHost = room.getRoomMembers().stream()
            .anyMatch(roomMember -> roomMember.getMemberId().equals(userId));

    if (!checkHost) {
      throw new ChatRoomNotHostException();
    }
  }

  @Transactional
  public List<ChatRoom> removeRoom(Long roomId, Long userId) {
    ChatRoom room = findRoom(roomId);

    validateCheckHost(room, userId);

    chatRoomRepository.deleteById(roomId);

    return getRoomList(userId);
  }

  @Transactional
  public List<ChatRoom> outRoom(Long roomId, Long userId) {
    ChatRoom room = findRoom(roomId);

    room.outMember(userId);

    return getRoomList(userId);
  }

  public void saveChatMessage(ChatMessageRequest chatMessageRequest) {

    ChatRoom room = findRoom(chatMessageRequest.chatRoomId());

    RoomMember roomMember = findRoomMember(chatMessageRequest.writerId());

    room.addChatLog(room, roomMember, chatMessageRequest);
  }

  private ChatRoom findRoom(Long roomId) {
    return chatRoomRepository.findById(roomId)
            .orElseThrow(ChatRoomEntityNotFoundException::new);
  }

  private RoomMember findRoomMember(Long memberId) {
    return roomMemberRepository.findById(memberId)
            .orElseThrow(RoomMemberNotFoundException::new);
  }

  public ChatRoom joinRoom(JoinRoomRequest joinRoomRequest) {
    ChatRoom room = findRoom(joinRoomRequest.roomId());

    RoomMember roomMember = RoomMember.init(joinRoomRequest.memberId(), joinRoomRequest.nickname());

    room.join(roomMember);

    return room;
  }

  public RoomMember getRoomMember(Long memberId) {
    return findRoomMember(memberId);
  }

  public ChatRoom updateNickname(Long roomId, Long memberId, String nickname) {
    ChatRoom room = findRoom(roomId);

    room.updateNickname(memberId, nickname);

    return room;
  }
}
