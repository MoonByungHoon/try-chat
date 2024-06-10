package study.trychat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chat_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_log_id")
  private ChatLog chatLog;
  private Long writerId;
  private String content;

  public ChatMessage(Long writerId, String content) {
    this.writerId = writerId;
    this.content = content;
  }
}
