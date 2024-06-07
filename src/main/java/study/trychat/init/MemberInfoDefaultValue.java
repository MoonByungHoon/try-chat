package study.trychat.init;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberInfoDefaultValue {
  PROFILE_IMG("defaultProfile.jpg"),
  BACKGROUND_IMG("defaultBackground.jpg"),
  PROFILE_PATH("https://my-side-project-bucket.s3.ap-northeast-2.amazonaws.com/");

  private final String value;
}
