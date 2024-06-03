package study.trychat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.FriendRequest;

import static study.trychat.entity.FriendStatus.BEST_FRIEND;
import static study.trychat.entity.FriendStatus.FRIEND;

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

  public static Friend fromMemberInfo(Long userId, MemberInfo fineMemberInfo) {
    return of(userId, fineMemberInfo.getId(), fineMemberInfo.getNickname(), fineMemberInfo.getProfileImg(),
            fineMemberInfo.getBackgroundImg(), fineMemberInfo.getProfileImgPath(), FRIEND);
  }

  private static Friend of(Long userId, Long friendId, String nickname, String profileImg,
                           String backgroundImg, String profileImgPath, FriendStatus friendStatus) {
    return new Friend(userId, friendId, nickname, profileImg, backgroundImg, profileImgPath, friendStatus);
  }

  public void updateStatus(FriendStatus friendStatus) {
    this.friendStatus = friendStatus;
  }

  public void bestFriend() {
    this.friendStatus = BEST_FRIEND;
  }

  public void block() {
    this.friendStatus = BEST_FRIEND;
  }

  public void updateProfile(FriendRequest friendRequest) {
    this.friendNickname = friendRequest.getFriendNickname();
  }
}
