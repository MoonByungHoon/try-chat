package study.trychat.dto.convert;

import study.trychat.dto.FriendBase.FriendResponse;
import study.trychat.entity.Friend;

public class FriendMapper {

  public static FriendResponse toFriendResponse(Friend findFriend) {
    return new FriendResponse(findFriend.getId(), findFriend.getFriendId(), findFriend.getNickname(),
            findFriend.getProfileImg(), findFriend.getBackgroundImg(), findFriend.getProfileImgPath(),
            findFriend.getFriendStatus());
  }
}
