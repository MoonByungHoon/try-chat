package study.trychat.dto.convert;

import study.trychat.dto.FriendBase.FriendShipResponse;
import study.trychat.entity.FriendShip;

public class FriendShipMapper {

  public static FriendShipResponse toFriendShipResponse(FriendShip findFriendShip) {
    return new FriendShipResponse(findFriendShip.getId(), findFriendShip.getMember().getId(), findFriendShip.getFriendId(),
            findFriendShip.getNickname(), findFriendShip.getGreetings(), findFriendShip.getProfileImg(),
            findFriendShip.getBackgroundImg(), findFriendShip.getProfileImgPath(), findFriendShip.getFriendStatus());
  }
}
