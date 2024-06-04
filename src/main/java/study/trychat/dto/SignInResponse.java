package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;

public record SignInResponse(
        Long id,
        String nickname,
        String greetings,
        String profileImg,
        String backgroundImg,
        String profileImgPath
) {
  @QueryProjection
  public SignInResponse(Long id, String nickname, String greetings, String profileImg, String backgroundImg, String profileImgPath) {
    this.id = id;
    this.nickname = nickname;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.backgroundImg = backgroundImg;
    this.profileImgPath = profileImgPath;
  }
}
