package study.trychat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class Member extends BaseEntity {

  @Column(nullable = false, unique = true, length = 340)
  private String email;
  @Column(nullable = false, length = 64)
  private String password;
  private String oAuthUsername;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Roles roles; //ADMIN, USER

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "member_info_id", nullable = false)
  private MemberInfo memberInfo;

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FriendShip> friendShips = new ArrayList<>();


  public static Member init(String email, String password, String nickname, String username) {
    return new Member(email, password, MemberInfo.init(nickname, username));
  }

  public Member(String email, MemberInfo memberInfo) {
    this.email = email;
    this.password = "null";
    this.roles = Roles.USER;
    this.memberInfo = memberInfo;
  }

  public Member(String email, String password, MemberInfo memberInfo) {
    this.email = email;
    this.password = password;
    this.roles = Roles.USER;
    this.memberInfo = memberInfo;
  }

  public void update(String email,
                     String password) {
    this.email = email;
    this.password = password;
  }

  public void addFriend(FriendShip newFriendShip) {
    this.friendShips.add(newFriendShip);
  }

  public void setOAuth2(String email, String oAuthUsername, String nickname, String username) {
    this.email = email;
    this.password = "OAuth2";
    this.oAuthUsername = oAuthUsername;
    this.roles = Roles.USER;
    this.memberInfo = MemberInfo.init(nickname, username);
  }
}
