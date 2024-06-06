package study.trychat.dto;

import jakarta.validation.constraints.*;

public sealed interface MemberBase permits MemberBase.MemberProfileResponse, MemberBase.MemberProfileUpdateRequest,
        MemberBase.MemberRemoveRequest, MemberBase.MemberResponse, MemberBase.MemberUpdateRequest {

  record MemberProfileResponse(
          Long id,
          String nickname,
          String greetings,
          String profileImg,
          String backgroundImg,
          String profileImgPath
  ) implements MemberBase {
  }

  record MemberProfileUpdateRequest(
          @NotNull(message = "{notBlank.id.validation.message}")
          Long id,
          @NotEmpty(message = "{notEmpty.nickname.validation.message}")
          @Size(min = 1, max = 20)
          String nickname,
          @NotNull(message = "{notNull.greetings.validation.message}")
          @Size(max = 60)
          String greetings
  ) implements MemberBase {
  }

  record MemberRemoveRequest(
          @NotBlank(message = "{notBlank.email.validation.message}")
          @Email(message = "{email.validation.message}", regexp = "^[^@]{5,64}@[^@]{8,255}")
          String email,
          @NotBlank(message = "{notBlank.password.validation.message}")
          @Pattern(
                  message = "{patten.password.validation.message}",
                  regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,64}$"
          )
          String password
  ) implements MemberBase {
  }

  record MemberResponse(
          Long id,
          String email
  ) implements MemberBase {
  }

  record MemberUpdateRequest(
          @NotBlank(message = "{notBlank.email.validation.message}")
          @Email(message = "{email.validation.message}", regexp = "^[^@]{5,64}@[^@]{8,255}")
          String email,
          @NotBlank(message = "{notBlank.password.validation.message}")
          @Pattern(
                  message = "{patten.password.validation.message}",
                  regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,64}$"
          )
          String password
  ) implements MemberBase {
  }
}
