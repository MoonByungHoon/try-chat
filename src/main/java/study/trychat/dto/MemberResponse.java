package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberResponse {

  private Long id;
  private String nickname;
  private String greetings;
  private String profileImg;
  private String profileImgPath;

  public static MemberResponse fromRequest(MemberRequest memberRequest) {
    return of(memberRequest.getId(), memberRequest.getNickname(), memberRequest.getGreetings(),
            memberRequest.getProfileImg(), memberRequest.getProfileImgPath());
  }

  public static MemberResponse of(Long id, String nickname, String greetings, String profileImg, String profileImgPath) {
    return new MemberResponse(id, nickname, greetings, profileImg, profileImgPath);
  }

  @QueryProjection
  public MemberResponse(Long id, String nickname, String greetings, String profileImg, String profileImgPath) {
    this.id = id;
    this.nickname = nickname;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.profileImgPath = profileImgPath;
  }
}
