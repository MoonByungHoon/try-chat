package study.trychat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;

@Getter
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

  public static void of(String username, String password) {
    new MemberAuthenticationDto(username, password);
  }

  @JsonCreator
  public MemberAuthenticationDto(@JsonProperty("username") String username,
                                 @JsonProperty("password") String password) {
    this.username = username;
    this.password = password;
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
