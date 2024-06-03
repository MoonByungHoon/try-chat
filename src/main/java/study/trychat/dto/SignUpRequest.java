package study.trychat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;

public record SignUpRequest(
        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.", regexp = "^[^@]{5,64}@[^@]{8,255}")
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,64}$")
        String password
) {
  public Member toEntityForSignUp(String username) {

    return Member.builder()
            .email(email)
            .password(password)
            .memberInfo(MemberInfo.init(username, username))
            .build();
  }
}
