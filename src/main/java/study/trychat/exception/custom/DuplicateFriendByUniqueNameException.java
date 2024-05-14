package study.trychat.exception.custom;

public class DuplicateUniqueNameException extends RuntimeException {
  private static String DUPLICATE_UNIQUE_NAME = "중복된 이름입니다.";

  public DuplicateUniqueNameException() {
    super(DUPLICATE_UNIQUE_NAME);
  }
}
