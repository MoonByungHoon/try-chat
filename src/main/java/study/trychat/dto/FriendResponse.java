package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import study.trychat.entity.FriendStatus;

@Getter
public class FriendResponse {
  private Long id;
  private Long friendId;
  private String friendNickname;
  private String friendProfileImg;
  private String friendProfileImgPath;
  private FriendStatus friendStatus;

  @QueryProjection
  public FriendResponse(Long id, Long friendId, String friendNickname, String friendProfileImg, String friendProfileImgPath, FriendStatus friendStatus) {
    this.id = id;
    this.friendId = friendId;
    this.friendNickname = friendNickname;
    this.friendProfileImg = friendProfileImg;
    this.friendProfileImgPath = friendProfileImgPath;
    this.friendStatus = friendStatus;
  }
}
