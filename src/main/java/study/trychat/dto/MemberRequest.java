package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberRequest extends MemberAuthenticationDto {

  private Long id;
  private String nickname;
  private String greetings;
  private String profileImg;
  private String profileImgPath;

  @QueryProjection
  public MemberRequest(Long id, String nickname, String greetings, String profileImg, String profileImgPath, String username) {
    super(id, username);
    this.id = id;
    this.nickname = nickname;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.profileImgPath = profileImgPath;
  }

  @QueryProjection
  public MemberRequest(Long id, String nickname, String greetings, String profileImg, String profileImgPath, String username, String password) {
    super(id, username, password);
    this.id = id;
    this.nickname = nickname;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.profileImgPath = profileImgPath;
  }
}
