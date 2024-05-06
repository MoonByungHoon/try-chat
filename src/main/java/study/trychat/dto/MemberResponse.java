package study.trychat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MemberResponse {

  private Long id;
  @NotEmpty(message = "공백은 혀용하지 않습니다.")
  @Size(min = 1, max = 20)
  private String nickname;
  @NotNull
  @Size(max = 60)
  private String greetings;
  @NotBlank
  @Size(min = 1)
  private String profileImg;
  @NotBlank
  @Size(min = 1)
  private String profileImgPath;
}
