package study.trychat.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsernameParam(
        @NotBlank(message = "username을 입력해주세요.")
        @Size(min = 4, max = 20)
        @Schema(description = "친구 검색에 사용되는 username")
        String username
) {
}
