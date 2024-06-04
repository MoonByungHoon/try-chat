package study.trychat.dto;

import jakarta.validation.constraints.NotBlank;

public record FriendNicknameUpdateRequest(
        @NotBlank
        Long friendId,
        @NotBlank
        String nickname
) {
}
