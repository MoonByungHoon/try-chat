package study.trychat.friend.domain;

import jakarta.persistence.*;
import lombok.*;
import study.trychat.common.BaseEntity;
import study.trychat.member.domain.Member;
import study.trychat.member.domain.MemberInfo;

import static study.trychat.friend.domain.FriendStatus.BEST_FRIEND;
import static study.trychat.friend.domain.FriendStatus.BLOCK;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendShip extends BaseEntity {
  @Column(nullable = false)
  private Long friendId;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String greetings;

  @Column(nullable = false)
  private String profileImg;

  @Column(nullable = false)
  private String backgroundImg;

  @Column(nullable = false)
  private String profileImgPath;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private FriendStatus friendStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public static FriendShip init(Member member, MemberInfo memberInfo) {
    return of(memberInfo.getId(), memberInfo.getNickname(), memberInfo.getGreetings(), memberInfo.getProfileImg(),
            memberInfo.getBackgroundImg(), memberInfo.getProfileImgPath(), FriendStatus.FRIEND, member);
  }

  private static FriendShip of(Long friendId, String nickname, String greetings, String profileImg,
                               String backgroundImg, String profileImgPath, FriendStatus friendStatus, Member member) {
    return new FriendShip(friendId, nickname, greetings, profileImg, backgroundImg, profileImgPath, friendStatus, member);
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

  public boolean checkId(Long memberId) {
    return memberId.equals(this.member.getId());
  }
}
