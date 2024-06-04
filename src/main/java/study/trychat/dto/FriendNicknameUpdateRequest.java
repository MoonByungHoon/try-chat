package study.trychat.dto;

import jakarta.validation.constraints.NotBlank;

public record FriendNicknameUpdateRequest(
        @NotBlank(message = "{notBlank.friendId.validation.message}")
        Long friendId,
        @NotBlank(message = "{notBlank.nickname.validation.message}")
        String nickname
) {
}
