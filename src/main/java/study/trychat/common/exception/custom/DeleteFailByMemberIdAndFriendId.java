package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public final class DeleteFailByMemberIdAndFriendId extends BaseCustomExceptions {
  public DeleteFailByMemberIdAndFriendId() {
    super(ErrorMessage.FRIEND_DELETE_FALSE);
  }
}
