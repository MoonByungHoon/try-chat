package study.trychat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
  private String friendProfileImgPath;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private FriendStatus friendStatus;

  public static Friend changeFromMemberInfo(Long userId, MemberInfo fineMemberInfo) {
    return of(userId, fineMemberInfo.getId(), fineMemberInfo.getNickname(), fineMemberInfo.getProfileImg(),
            fineMemberInfo.getProfileImgPath(), FRIEND);
  }

  private static Friend of(Long userId, Long friendId, String nickname, String profileImg,
                           String profileImgPath, FriendStatus friendStatus) {
    return new Friend(userId, friendId, nickname, profileImg, profileImgPath, friendStatus);
  }

  public Friend(Long memberId, Long friendId, String friendNickname, String friendProfileImg,
                String friendProfileImgPath, FriendStatus friendStatus) {
    this.memberId = memberId;
    this.friendId = friendId;
    this.friendNickname = friendNickname;
    this.friendProfileImg = friendProfileImg;
    this.friendProfileImgPath = friendProfileImgPath;
    this.friendStatus = friendStatus;
  }
}
