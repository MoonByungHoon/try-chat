package study.trychat.exception.custom;

public class DuplicateUsernameException extends RuntimeException {
  private static String DUPLICATE_USER = "이미 가입된 회원입니다.";

  public DuplicateUsernameException() {
    super(DUPLICATE_USER);
  }
}
