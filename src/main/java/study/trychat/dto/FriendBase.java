package study.trychat.dto;

import jakarta.validation.constraints.NotBlank;
import study.trychat.entity.FriendStatus;

public sealed interface FriendBase permits FriendBase.FriendNicknameUpdateRequest, FriendBase.FriendResponse {

  record FriendNicknameUpdateRequest(
          @NotBlank(message = "{notBlank.friendId.validation.message}")
          Long friendId,
          @NotBlank(message = "{notBlank.nickname.validation.message}")
          String nickname
  ) implements FriendBase {
  }

  record FriendResponse(
          Long id,
          Long friendId,
          String nickname,
          String profileImg,
          String backgroundImg,
          String profileImgPath,
          FriendStatus friendStatus
  ) implements study.trychat.dto.FriendBase {
  }
}
