package study.trychat.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberProfileUpdateRequest(
        @NotNull(message = "{notBlank.id.validation.message}")
        Long id,
        @NotEmpty(message = "{notEmpty.nickname.validation.message}")
        @Size(min = 1, max = 20)
        String nickname,
        @NotNull(message = "{notNull.greetings.validation.message}")
        @Size(max = 60)
        String greetings
) {
}
