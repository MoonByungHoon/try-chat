package study.trychat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public sealed interface SignBase permits SignBase.SignInRequest, SignBase.SingInResponse, SignBase.SignUpRequest {
  record SignInRequest(
          @NotBlank(message = "{notBlank.email.validation.message}")
          @Email(message = "{email.validation.message}", regexp = "^[^@]{5,64}@[^@]{8,255}")
          String email,
          @NotBlank(message = "{notBlank.password.validation.message}")
          @Pattern(
                  message = "{patten.password.validation.message}",
                  regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,64}$"
          )
          String password
  ) implements SignBase {
  }

  record SingInResponse(
          Long id,
          String nickname,
          String greetings,
          String profileImg,
          String backgroundImg,
          String profileImgPath
  ) implements SignBase {
  }

  record SignUpRequest(
          @NotBlank(message = "{notBlank.email.validation.message}")
          @Email(message = "{email.validation.message}", regexp = "^[^@]{5,64}@[^@]{8,255}")
          String email,
          @NotBlank(message = "{notBlank.password.validation.message}")
          @Pattern(
                  message = "{patten.password.validation.message}",
                  regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,64}$"
          )
          String password
  ) implements SignBase {
  }
}
