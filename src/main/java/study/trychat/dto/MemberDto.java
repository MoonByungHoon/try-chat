package study.trychat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.trychat.entity.Member;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
  private Long id;
  private String username;
  private String password;

  public MemberDto(String username) {
    this.username = username;
  }

  public Member toEntity() {
    return Member.builder()
            .username(this.username)
            .password(this.password)
            .build();
  }
}
