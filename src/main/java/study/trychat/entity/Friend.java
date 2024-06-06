package study.trychat.entity;

import jakarta.persistence.*;
import lombok.*;

import static study.trychat.entity.FriendStatus.BEST_FRIEND;
import static study.trychat.entity.FriendStatus.BLOCK;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class Friend extends BaseEntity {

  @Column(name = "member_id", nullable = false)
  private Long memberId;
  @Column(nullable = false)
  private Long friendId;
  @Column(nullable = false)
  private String nickname;
  @Column(nullable = false)
  private String profileImg;
  @Column(nullable = false)
  private String backgroundImg;
  @Column(nullable = false)
  private String profileImgPath;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private FriendStatus friendStatus;

  public static Friend init(Long userId, MemberInfo memberInfo) {
    return of(userId, memberInfo.getId(), memberInfo.getNickname(), memberInfo.getProfileImg(),
            memberInfo.getBackgroundImg(), memberInfo.getProfileImgPath(), FriendStatus.FRIEND);
  }

  private static Friend of(Long userId, Long friendId, String nickname, String profileImg,
                           String backgroundImg, String profileImgPath, FriendStatus friendStatus) {
    return new Friend(userId, friendId, nickname, profileImg, backgroundImg, profileImgPath, friendStatus);
  }

  public void bestFriend() {
    this.friendStatus = BEST_FRIEND;
  }

  public void block() {
    this.friendStatus = BLOCK;
  }

  public void updateProfile(String nickname) {
    this.nickname = nickname;
  }
}
