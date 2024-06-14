package study.trychat.chat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.chat.ChatBase.ChatMessageRequest;
import study.trychat.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {

  @Column(nullable = false, length = 50)
  private String name;

  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RoomMember> roomMembers = new ArrayList<>();

  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ChatLog> chatLogs = new ArrayList<>();

  public static ChatRoom init(String roomName, Long userId, String nickname) {
    return new ChatRoom(roomName, userId, nickname);
  }

  public void join(RoomMember roomMember) {
    this.roomMembers.add(roomMember);
    roomMember.updateChatRoom(this);
  }

  public ChatRoom(String name, Long userId, String nickname) {
    this.name = name;
    this.roomMembers.add(RoomMember.init(userId, nickname));
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void outMember(Long userId) {
    this.getRoomMembers().removeIf(roomMember -> roomMember.getMemberId().equals(userId));
  }

  public void addChatLog(ChatRoom room, RoomMember roomMember, ChatMessageRequest chatMessageRequest) {
    this.chatLogs.add(new ChatLog(roomMember.getMemberId(), roomMember.getNickname(),
            roomMember.getProfileImg(), roomMember.getProfileImgPath(), chatMessageRequest.message(), room));
  }

  public void updateNickname(Long memberId, String nickname) {
    this.getRoomMembers().stream()
            .filter(roomMember -> roomMember.getMemberId().equals(memberId))
            .findFirst()
            .ifPresent(roomMember -> roomMember.updateNickname(nickname));
  }
}
