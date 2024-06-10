package study.trychat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {
  @Column(nullable = false, length = 50)
  private String name;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "chat_log_id")
  private ChatLog chatLog;

  public ChatRoom(String name, Long originId, String nickname) {
    this.name = name;
    chatLog.createRoom(originId, nickname);
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void out(Long userId) {
    List<ChatMember> chatMembers = chatLog.getChatMembers();
    chatMembers.removeIf(chatMember -> chatMember.getOriginId().equals(userId));
  }
}
