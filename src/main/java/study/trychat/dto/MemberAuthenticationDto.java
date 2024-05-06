package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuthenticationDto {

  private Long id;

  @NotBlank(message = "이메일을 입력해주세요.")
  @Size(max = 320)
  @Email(message = "올바른 이메일 주소를 입력해주세요.")
  private String username;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  @Size(min = 8, max = 64)
  private String password;

  @QueryProjection
  public MemberAuthenticationDto(Long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public MemberAuthenticationDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  protected MemberAuthenticationDto(Long id, String username) {
    this.id = id;
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
