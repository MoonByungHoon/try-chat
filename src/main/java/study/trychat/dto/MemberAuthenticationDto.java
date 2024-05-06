package study.trychat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberAuthenticationDto {

  @NotBlank(message = "이메일을 입력해주세요.")
  @Size(max = 320)
  @Email(message = "올바른 이메일 주소를 입력해주세요.")
  private String username;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Size(min = 8, max = 64)
  private String password;

  protected MemberAuthenticationDto(String username) {
    this.username = username;
  }

  public void validateUsername(MemberAuthenticationDto authenticationDto) {

    String[] split = authenticationDto.username.split("@");

    if (split[0].length() > 64 || split[1].length() > 255) {
      throw new IllegalArgumentException("올바른 이메일 주소를 입력해주세요.");
    }
  }

  public Member toEntity() {

    return Member.builder()
            .username(username)
            .password(password)
            .memberInfo(new MemberInfo("unknown", "", "default.jpg", "/local/default/"))
            .build();
  }
}
