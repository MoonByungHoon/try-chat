package study.trychat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import study.trychat.entity.FriendStatus;

public sealed interface FriendBase permits FriendBase.FriendNicknameUpdateRequest, FriendBase.FriendShipResponse {

  record FriendNicknameUpdateRequest(
          @NotBlank(message = "친구의 ID값이 비어있습니다.")
          Long friendId,
          @NotBlank(message = "친구의 nickname을 입력해주세요.")
          String nickname
  ) implements FriendBase {
  }

  record FriendShipResponse(
          @Schema(description = "친구 추가된 자료의 ID값")
          Long id,
          @Schema(description = "사용자 ID")
          Long memberId,
          @Schema(description = "친구의 ID")
          Long friendId,
          @Schema(description = "친구의 nickname")
          String nickname,
          @Schema(description = "친구의 상태메세지")
          String greetings,
          @Schema(description = "친구의 프로필 이미지 파일명")
          String profileImg,
          @Schema(description = "친구의 배경사진 이미지 파일명")
          String backgroundImg,
          @Schema(description = "이미지 파일이 저장된 경로")
          String profileImgPath,
          @Schema(description = "친구 관계 상태")
          FriendStatus friendStatus
  ) implements study.trychat.dto.FriendBase {
  }
}
