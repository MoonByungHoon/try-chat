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
  private String email; // email
  @Column(nullable = false, length = 64)
  private String password;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "member_info_id", nullable = false)
  private MemberInfo memberInfo;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "member_id")
  private List<Friend> friendList = new ArrayList<>();

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Roles roles; //ADMIN, USER

  public static Member init(String email, String password, String nickname, String username) {
    return new Member(email, password, MemberInfo.init(nickname, username));
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
}
