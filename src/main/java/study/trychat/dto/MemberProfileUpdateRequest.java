package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemberProfileUpdateRequest(
        @NotEmpty(message = "{notEmpty.nickname.validation.message}")
        @Size(min = 1, max = 20)
        String nickname,
        @NotNull(message = "{notNull.greetings.validation.message}")
        @Size(max = 60)
        String greetings,
        @NotBlank(message = "{notBlank.profileImg.validation.message}")
        @Size(min = 1)
        String profileImg,
        @NotBlank(message = "{notBlank.profileImgPath.validation.message}")
        @Size(min = 1)
        String backgroundImg,
        @NotBlank(message = "{notBlank.backgroundImg.validation.message}")
        @Size(min = 1)
        String profileImgPath
) {
  @QueryProjection
  public MemberProfileUpdateRequest(String nickname, String greetings, String profileImg, String backgroundImg, String profileImgPath) {
    this.nickname = nickname;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.backgroundImg = backgroundImg;
    this.profileImgPath = profileImgPath;
  }
}
