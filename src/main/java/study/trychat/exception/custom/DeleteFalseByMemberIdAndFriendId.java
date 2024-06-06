package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class DeleteFalseByMemberIdAndFriendId extends RuntimeException {
  public DeleteFalseByMemberIdAndFriendId() {
    super(ErrorMessage.FRIEND_DELETE_FALSE.getMessage());
  }
}
