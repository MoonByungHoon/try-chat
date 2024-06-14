package study.trychat.exception.custom;

public class EntityNotFoundException extends RuntimeException {

  private static final String ENTITY_NOT_FOUND = "일치하는 회원이 없습니다.";

  public EntityNotFoundException() {
    super(ENTITY_NOT_FOUND);
  }
}
