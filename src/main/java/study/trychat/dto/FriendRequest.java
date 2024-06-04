package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import study.trychat.entity.FriendStatus;

public record FriendRequest(
        Long friendId,
        String friendNickname,
        FriendStatus friendStatus
) {
  @QueryProjection
  public FriendRequest(Long friendId, String friendNickname, FriendStatus friendStatus) {
    this.friendId = friendId;
    this.friendNickname = friendNickname;
    this.friendStatus = friendStatus;
  }
}
