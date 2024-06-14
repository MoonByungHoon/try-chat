package study.trychat.exception.custom;

public class PrimaryKeyMismatchException extends RuntimeException {
  private static String PRIMARY_KEY_MISMATCH = "대상이 일치하지 않습니다.";

  public PrimaryKeyMismatchException() {
    super(PRIMARY_KEY_MISMATCH);
  }
}
