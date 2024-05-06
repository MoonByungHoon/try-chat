package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberRequest {

  private Long id;
  private String nickname;
  private String greetings;
  private String profileImg;
  private String profileImgPath;

  @QueryProjection
  public MemberRequest(Long id, String nickname, String greetings, String profileImg, String profileImgPath) {
    this.id = id;
    this.nickname = nickname;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.profileImgPath = profileImgPath;
  }
}
