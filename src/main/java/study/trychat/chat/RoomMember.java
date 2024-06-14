package study.trychat.chat;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.common.BaseEntity;

import static study.trychat.member.domain.MemberInfoDefault.PROFILE_IMG;
import static study.trychat.member.domain.MemberInfoDefault.PROFILE_PATH;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomMember extends BaseEntity {

  @Column(nullable = false)
  private Long memberId;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String profileImg;

  @Column(nullable = false)
  private String profileImgPath;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  public RoomMember(Long memberId, String nickname) {
    this.memberId = memberId;
    this.nickname = nickname;
    this.profileImg = PROFILE_IMG.getValue();
    this.profileImgPath = PROFILE_PATH.getValue();
  }

  public static RoomMember init(Long memberId, String nickname) {
    return new RoomMember(memberId, nickname);
  }

  public void updateChatRoom(ChatRoom chatRoom) {
    this.chatRoom = chatRoom;
  }

  public void updateNickname(String nickname) {
    this.nickname = nickname;
  }
}
