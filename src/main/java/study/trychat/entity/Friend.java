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
  private String friendNickname;
  @Column(nullable = false)
  private String friendProfileImg;
  @Column(nullable = false)
  private String friendBackgroundImg;
  @Column(nullable = false)
  private String friendProfileImgPath;
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

  public void updateBestStatus() {
    this.friendStatus = BEST_FRIEND;
  }

  public void bestFriend() {
    this.friendStatus = BEST_FRIEND;
  }

  public void block() {
    this.friendStatus = BEST_FRIEND;
  }

  public void updateProfile(FriendNicknameUpdateRequest nicknameUpdateRequest) {
    this.friendNickname = nicknameUpdateRequest.nickname();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Friend friend = (Friend) o;
    return Objects.equals(memberId, friend.memberId) && Objects.equals(friendId, friend.friendId) && Objects.equals(friendNickname, friend.friendNickname) && Objects.equals(friendProfileImg, friend.friendProfileImg) && Objects.equals(friendBackgroundImg, friend.friendBackgroundImg) && Objects.equals(friendProfileImgPath, friend.friendProfileImgPath) && friendStatus == friend.friendStatus;
  }

  @Override
  public int hashCode() {
    return Objects.hash(memberId, friendId, friendNickname, friendProfileImg, friendBackgroundImg, friendProfileImgPath, friendStatus);
  }
}
