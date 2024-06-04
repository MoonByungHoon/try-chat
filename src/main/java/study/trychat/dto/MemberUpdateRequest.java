package study.trychat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberUpdateRequest(
        @NotBlank(message = "{notBlank.email.validation.message}")
        @Email(message = "{email.validation.message}", regexp = "^[^@]{5,64}@[^@]{8,255}")
        String email,
        @NotBlank(message = "{notBlank.password.validation.message}")
        @Pattern(
                message = "{patten.password.validation.message}",
                regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,64}$"
        )
        String password
) {
}
