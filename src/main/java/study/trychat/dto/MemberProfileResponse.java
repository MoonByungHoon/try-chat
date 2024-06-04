package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import study.trychat.entity.MemberInfo;

public record MemberProfileResponse(
        Long id,
        String nickname,
        String greetings,
        String profileImg,
        String backgroundImg,
        String profileImgPath
) {
  public static MemberProfileResponse change(MemberInfo memberInfo) {
    return of(memberInfo.getId(), memberInfo.getNickname(), memberInfo.getGreetings(),
            memberInfo.getProfileImg(), memberInfo.getBackgroundImg(), memberInfo.getProfileImgPath());
  }

  public static MemberProfileResponse of(Long id, String nickname, String greetings, String profileImg,
                                         String backgroundImg, String profileImgPath) {
    return new MemberProfileResponse(id, nickname, greetings, profileImg, backgroundImg, profileImgPath);
  }

  @QueryProjection
  public MemberProfileResponse(Long id, String nickname, String greetings, String profileImg, String backgroundImg, String profileImgPath) {
    this.id = id;
    this.nickname = nickname;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.backgroundImg = backgroundImg;
    this.profileImgPath = profileImgPath;
  }
}
