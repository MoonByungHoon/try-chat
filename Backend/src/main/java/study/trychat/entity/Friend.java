package study.trychat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.FriendRequest;

import static study.trychat.entity.FriendStatus.FRIEND;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
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

  public Friend(Long memberId, Long friendId, String friendNickname, String friendProfileImg,
                String friendBackgroundImg, String friendProfileImgPath, FriendStatus friendStatus) {
    this.memberId = memberId;
    this.friendId = friendId;
    this.friendNickname = friendNickname;
    this.friendProfileImg = friendProfileImg;
    this.friendBackgroundImg = friendBackgroundImg;
    this.friendProfileImgPath = friendProfileImgPath;
    this.friendStatus = friendStatus;
  }

  public void updateStatus(FriendStatus friendStatus) {
    this.friendStatus = friendStatus;
  }

  public void updateProfile(FriendRequest friendRequest) {
    this.friendNickname = friendRequest.getFriendNickname();
  }
}