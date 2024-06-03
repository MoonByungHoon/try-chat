package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import study.trychat.entity.FriendStatus;

@Data
public class FriendRequest {
  private final Long friendId;
  private final String friendNickname;
  private final FriendStatus friendStatus;

  @QueryProjection
  public FriendRequest(Long friendId, String friendNickname, FriendStatus friendStatus) {
    this.friendId = friendId;
    this.friendNickname = friendNickname;
    this.friendStatus = friendStatus;
  }
}
