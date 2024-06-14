package study.trychat.chat;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.common.BaseEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatLog extends BaseEntity {

  @Column(nullable = false)
  private Long writerId;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String profileImg;

  @Column(nullable = false)
  private String profileImgPath;

  @Column(nullable = false)
  private String message;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  public void setMessage(String s) {
  }
}
