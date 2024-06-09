package study.trychat.member.domain;

import jakarta.persistence.*;
import lombok.*;
import study.trychat.common.BaseEntity;
import study.trychat.friend.domain.FriendShip;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
  @Column(nullable = false, unique = true, length = 340)
  private String email;

  @Column(nullable = false, length = 64)
  private String password;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "member_info_id", nullable = false)
  private MemberInfo memberInfo;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FriendShip> friendShips = new ArrayList<>();

  public static Member init(String email, String password, String nickname, String username) {
    return new Member(email, password, MemberInfo.init(nickname, username));
  }

  public Member(String email, String password, MemberInfo memberInfo) {
    this.email = email;
    this.password = password;
    this.role = Role.USER;
    this.memberInfo = memberInfo;
  }

  public void update(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public void addFriend(FriendShip friendShip) {
    this.friendShips.add(friendShip);
  }
}
