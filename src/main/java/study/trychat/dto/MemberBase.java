package study.trychat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public sealed interface MemberBase permits MemberBase.MemberProfileResponse, MemberBase.MemberProfileUpdateRequest,
        MemberBase.MemberRemoveRequest, MemberBase.MemberResponse, MemberBase.MemberUpdateRequest {

  record MemberProfileResponse(
          @Schema(description = "사용자 ID")
          Long id,
          @Schema(description = "다른 유저에게 보여주는 이름")
          String nickname,
          @Schema(description = "상태메세지")
          String greetings,
          @Schema(description = "프로필 이미지 파일명")
          String profileImg,
          @Schema(description = "배경사진 이미지 파일명")
          String backgroundImg,
          @Schema(description = "이미지 파일이 저장된 경로")
          String profileImgPath
  ) implements MemberBase {
  }

  record MemberProfileUpdateRequest(
          @NotNull(message = "아이디가 비어있습니다.")
          Long id,
          @NotEmpty(message = "닉네임을 입력해주세요.")
          @Size(min = 1, max = 20)
          @Schema(description = "다른 유저에게 보여주는 이름", example = "mbh")
          String nickname,
          @NotNull(message = "상태메세지를 입력해주세요.")
          @Size(max = 60)
          @Schema(description = "상태메세지")
          String greetings
  ) implements MemberBase {
  }

  record MemberRemoveRequest(
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
  ) implements MemberBase {
  }

  record MemberResponse(
          @Schema(description = "사용자의 ID")
          Long id,
          @Schema(description = "이메일", example = "testemail@test.co.kr")
          String email
  ) implements MemberBase {
  }

  record MemberUpdateRequest(
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
  ) implements MemberBase {
  }
}
