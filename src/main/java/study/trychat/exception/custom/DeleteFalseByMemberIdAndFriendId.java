package study.trychat.exception.custom;

public class DeleteFalseByMemberIdAndFriendId extends RuntimeException {

  private static String DELETE_FALSE = "친구 삭제에 실패하였습니다.";

  public DeleteFalseByMemberIdAndFriendId() {
    super(DELETE_FALSE);
  }
}
