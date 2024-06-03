package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SignInResponse {

  private static Long id;
  private static String nickname;
  private static String username;
  private static String greetings;
  private static String profileImg;
  private static String backgroundImg;
  private static String profileImgPath;

  @QueryProjection
  public SignInResponse(Long id, String nickname, String username, String greetings, String profileImg,
                        String backgroundImg, String profileImgPath) {
    this.id = id;
    this.nickname = nickname;
    this.username = username;
    this.greetings = greetings;
    this.profileImg = profileImg;
    this.backgroundImg = backgroundImg;
    this.profileImgPath = profileImgPath;
  }
}
