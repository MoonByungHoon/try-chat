package study.trychat.httptest;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import study.trychat.entity.FriendStatus;

@Getter
public class FriendTest {
  private Long id;
  private Long friendId;
  private String friendNickname;
  private String friendProfileImg;
  private String friendProfileImgPath;
  @Enumerated(EnumType.STRING)
  private FriendStatus friendStatus;
}
