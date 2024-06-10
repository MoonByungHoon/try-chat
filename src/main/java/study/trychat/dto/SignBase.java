package study.trychat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public sealed interface SignBase permits SignBase.SignInRequest, SignBase.SingInResponse, SignBase.SignUpRequest {

  record SignInRequest(
          @NotBlank(message = "이메일을 입력해주세요.")
          @Email(message = "이메일 양식을 지켜주세요.", regexp = "^[^@]{5,64}@[^@]{8,255}")
          @Schema(description = "이메일", example = "testemail@test.co.kr")
          String email,
          @NotBlank(message = "비밀번호를 입력해주세요.")
          @Pattern(
                  message = "비밀번호 양식을 지켜주세요.",
                  regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,64}$"
          )
          @Schema(description = "비밀번호", example = "testpassword@1")
          String password
  ) implements SignBase {
  }

  record SingInResponse(
          @Schema(description = "유저 Id", example = "1")
          Long id,
          @Schema(description = "유저가 모두에게 보여주는 이름", example = "문병훈")
          String nickname,
          @Schema(description = "상태 메세지", example = "안녕하세요. swagger코드량 미쳤네")
          String greetings,
          @Schema(description = "사용자의 프로필 사진", example = "default.jpg")
          String profileImg,
          @Schema(description = "사용자의 배경 사진", example = "default.jpg")
          String backgroundImg,
          @Schema(description = "사용자의 프로필, 배경사진 저장 경로", example = "/user/default/")
          String profileImgPath,
          @Schema(description = "계정의 권한", example = "USER")
          String role
  ) implements SignBase {
  }

  record SignUpRequest(
          @NotBlank(message = "이메일을 입력해주세요.")
          @Email(message = "이메일 양식을 지켜주세요.", regexp = "^[^@]{5,64}@[^@]{8,255}")
          @Schema(description = "이메일", example = "testemail@test.co.kr")
          String email,
          @NotBlank(message = "비밀번호를 입력해주세요.")
          @Pattern(
                  message = "비밀번호 양식을 지켜주세요.",
                  regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,64}$"
          )
          @Schema(description = "비밀번호", example = "testpassword@1")
          String password
  ) implements SignBase {
  }
}
