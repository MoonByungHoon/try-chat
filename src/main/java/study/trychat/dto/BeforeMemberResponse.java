package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BeforeMemberResponse {

  private Long id;
  private String nickname;
  private String uniqueName;
  private String greetings;
  private String profileImg;
  private String backgroundImg;
  private String profileImgPath;

  public static BeforeMemberResponse fromRequest(BeforeMemberRequest beforeMemberRequest) {
    return of(beforeMemberRequest.getId(), beforeMemberRequest.getNickname(), beforeMemberRequest.getUniqueName(), beforeMemberRequest.getGreetings(),
            beforeMemberRequest.getProfileImg(), beforeMemberRequest.getBackgroundImg(), beforeMemberRequest.getProfileImgPath());
  }

  public static BeforeMemberResponse of(Long id, String nickname, String uniqueName, String greetings, String profileImg,
                                        String backgroundImg, String profileImgPath) {
    return new BeforeMemberResponse(id, nickname, uniqueName, greetings, profileImg, backgroundImg, profileImgPath);
  }

  @QueryProjection
  public BeforeMemberResponse(Long id, String nickname, String uniqueName, String greetings, String profileImg,
                              String backgroundImg, String profileImgPath) {
    this.id = id;
    this.nickname = nickname;
    this.uniqueName = uniqueName;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.backgroundImg = backgroundImg;
    this.profileImgPath = profileImgPath;
  }
}
