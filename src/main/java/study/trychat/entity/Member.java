package study.trychat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.exception.custom.PrimaryKeyMismatchException;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true, length = 340)
  private String username;
  @Column(nullable = false, length = 64)
  private String password;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "member_info_id")
  private MemberInfo memberInfo;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "member_id")
  private List<Friend> friendList = new ArrayList<>();

  public void update(MemberAuthenticationDto authenticationDto) {
    this.username = authenticationDto.getUsername();
    this.password = authenticationDto.getPassword();
  }

  public void checkId(Long id) {
    if (!(this.id == id)) {
      throw new PrimaryKeyMismatchException();
    }
  }
}
