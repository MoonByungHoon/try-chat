package study.trychat.Dto;

import lombok.*;
import study.trychat.entity.Member;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
  private Long id;
  private String username;
  private String password;

  public Member toEntity() {
    return Member.builder()
            .username(this.username)
            .build();
  }
}
