package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import study.trychat.entity.FriendStatus;

@Getter
public class FriendRequest {
  private Long friendId;
  private String friendNickname;
  @Enumerated(EnumType.STRING)
  private FriendStatus friendStatus;

  @QueryProjection
  public FriendRequest(Long friendId, String friendNickname, FriendStatus friendStatus) {
    this.friendId = friendId;
    this.friendNickname = friendNickname;
    this.friendStatus = friendStatus;
  }
}
