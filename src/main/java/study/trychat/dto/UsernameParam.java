package study.trychat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsernameParam(
        @NotBlank(message = "{notBlank.username.validation.message}")
        @Size(min = 4, max = 20)
        String username
) {
}
