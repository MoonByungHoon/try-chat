package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;
import study.trychat.entity.Friend;
import study.trychat.entity.FriendStatus;

public record FriendResponse(
        Long id,
        Long friendId,
        String nickname,
        String profileImg,
        String backgroundImg,
        String profileImgPath,
        FriendStatus friendStatus
) {
  public static FriendResponse changeResponse(Friend friend) {
    return of(friend.getId(), friend.getFriendId(), friend.getNickname(), friend.getProfileImg(),
            friend.getBackgroundImg(), friend.getProfileImgPath(), friend.getFriendStatus());
  }

  public static FriendResponse of(Long id, Long friendId, String friendNickname, String friendProfileImg,
                                  String friendBackgroundImg, String friendProfileImgPath, FriendStatus friendStatus) {
    return new FriendResponse(id, friendId, friendNickname, friendProfileImg, friendBackgroundImg,
            friendProfileImgPath, friendStatus);
  }

  @QueryProjection
  public FriendResponse(Long id, Long friendId, String nickname, String profileImg,
                        String backgroundImg, String profileImgPath, FriendStatus friendStatus) {
    this.id = id;
    this.friendId = friendId;
    this.nickname = nickname;
    this.profileImg = profileImg;
    this.backgroundImg = backgroundImg;
    this.profileImgPath = profileImgPath;
    this.friendStatus = friendStatus;
  }
}
