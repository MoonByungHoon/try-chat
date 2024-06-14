package study.trychat.exception.custom;

public class DuplicateFriendByUniqueNameException extends RuntimeException {
  private static String DUPLICATE_UNIQUE_NAME = "이미 친구 추가가 되어있습니다.";

  public DuplicateFriendByUniqueNameException() {
    super(DUPLICATE_UNIQUE_NAME);
  }
}
