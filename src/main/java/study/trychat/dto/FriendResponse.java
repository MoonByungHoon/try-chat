package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import study.trychat.entity.Friend;
import study.trychat.entity.FriendStatus;

@Data
public class FriendResponse {
  private Long id;
  private Long friendId;
  private String friendNickname;
  private String friendProfileImg;
  private String friendBackgroundImg;
  private String friendProfileImgPath;
  private FriendStatus friendStatus;

  public static FriendResponse fromRequest(Friend friend) {
    return of(friend.getId(), friend.getFriendId(), friend.getFriendNickname(), friend.getFriendProfileImg(),
            friend.getFriendBackgroundImg(), friend.getFriendProfileImgPath(), friend.getFriendStatus());
  }

  public static FriendResponse of(Long id, Long friendId, String friendNickname, String friendProfileImg,
                                  String friendBackgroundImg, String friendProfileImgPath, FriendStatus friendStatus) {
    return new FriendResponse(id, friendId, friendNickname, friendProfileImg, friendBackgroundImg,
            friendProfileImgPath, friendStatus);
  }

  @QueryProjection
  public FriendResponse(Long id, Long friendId, String friendNickname, String friendProfileImg,
                        String friendBackgroundImg, String friendProfileImgPath, FriendStatus friendStatus) {
    this.id = id;
    this.friendId = friendId;
    this.friendNickname = friendNickname;
    this.friendProfileImg = friendProfileImg;
    this.friendBackgroundImg = friendBackgroundImg;
    this.friendProfileImgPath = friendProfileImgPath;
    this.friendStatus = friendStatus;
  }
}
