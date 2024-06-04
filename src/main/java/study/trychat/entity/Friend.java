package study.trychat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.FriendNicknameUpdateRequest;

import java.util.Objects;

import static study.trychat.entity.FriendStatus.BEST_FRIEND;
import static study.trychat.entity.FriendStatus.BLOCK;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

  public void updateProfile(FriendNicknameUpdateRequest nicknameUpdateRequest) {
    this.nickname = nicknameUpdateRequest.nickname();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Friend friend = (Friend) o;
    return Objects.equals(memberId, friend.memberId) && Objects.equals(friendId, friend.friendId) && Objects.equals(nickname, friend.nickname) && Objects.equals(profileImg, friend.profileImg) && Objects.equals(backgroundImg, friend.backgroundImg) && Objects.equals(profileImgPath, friend.profileImgPath) && friendStatus == friend.friendStatus;
  }

  @Override
  public int hashCode() {
    return Objects.hash(memberId, friendId, nickname, profileImg, backgroundImg, profileImgPath, friendStatus);
  }
}
