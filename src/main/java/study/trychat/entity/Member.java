package study.trychat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.dto.MemberUpdateRequest;
import study.trychat.exception.custom.PrimaryKeyMismatchException;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

  @Enumerated(EnumType.STRING)
  private Roles roles; //ADMIN, USER

  public void update(MemberUpdateRequest memberUpdateRequest) {
    this.email = memberUpdateRequest.email();
    this.password = memberUpdateRequest.password();
  }

  public void checkId(Long id) {
    if (!(id.equals(super.getId()))) {
      throw new PrimaryKeyMismatchException();
    }
  }
}
