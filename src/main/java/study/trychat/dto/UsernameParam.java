package study.trychat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsernameParam(
        @NotBlank
        @Size(min = 4, max = 20)
        String username
) {
}
