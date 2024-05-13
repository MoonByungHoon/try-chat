package study.trychat.dto;

import study.trychat.entity.FriendStatus;

public class FriendResponse {
  private Long id;
  private Long friendId;
  private String friendNickname;
  private String friendProfileImg;
  private String friendProfileImgPath;
  private FriendStatus friendStatus;
}
