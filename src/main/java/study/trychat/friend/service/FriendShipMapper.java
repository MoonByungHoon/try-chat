package study.trychat.friend.service;

import study.trychat.friend.dto.FriendBase.FriendShipResponse;
import study.trychat.friend.domain.FriendShip;

public class FriendShipMapper {

  public static FriendShipResponse toFriendResponse(FriendShip findFriendShip) {
    return new FriendShipResponse(findFriendShip.getId(), findFriendShip.getMember().getId(), findFriendShip.getFriendId(),
            findFriendShip.getNickname(), findFriendShip.getGreetings(), findFriendShip.getProfileImg(),
            findFriendShip.getBackgroundImg(), findFriendShip.getProfileImgPath(), findFriendShip.getFriendStatus());
  }
}
