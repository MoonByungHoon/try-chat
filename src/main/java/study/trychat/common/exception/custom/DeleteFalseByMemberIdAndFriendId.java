package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class DeleteFalseByMemberIdAndFriendId extends RuntimeException {
  public DeleteFalseByMemberIdAndFriendId() {
    super(ErrorMessage.FRIEND_DELETE_FALSE.getMessage());
  }
}
