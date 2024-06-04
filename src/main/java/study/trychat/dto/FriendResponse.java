package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import study.trychat.entity.Friend;
import study.trychat.entity.FriendStatus;

public record FriendResponse(
        Long id,
        Long friendId,
        String friendNickname,
        String friendProfileImg,
        String friendBackgroundImg,
        String friendProfileImgPath,
        FriendStatus friendStatus
) {
  public static FriendResponse changeResponse(Friend friend) {
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
