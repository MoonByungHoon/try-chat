package study.trychat.exception.custom;

import study.trychat.message.ErrorMessage;

public class DeleteFalseByMemberIdAndFriendId extends RuntimeException {
  public DeleteFalseByMemberIdAndFriendId() {
    super(ErrorMessage.FRIEND_DELETE_FALSE.getMessage());
  }
}
