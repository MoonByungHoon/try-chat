package study.trychat.exception;

public enum ErrorMessage {
  S3_FILE_NOT_FOUND("파일 형식이 잘못되었습니다."),
  S3_FILE_IS_NULL("파일이 존재하지 않습니다."),
  FRIEND_DELETE_FALSE("친구 삭제에 실패하였습니다."),
  FRIEND_ADD_DUPLICATE_UNIQUE_NAME("이미 친구 추가가 되어있습니다."),
  SIGN_UP_DUPLICATE_USER("이미 가입된 회원입니다."),
  MEMBER_ENTITY_NOT_FOUND("일치하는 회원이 없습니다."),
  PRIMARY_KEY_MISMATCH("요청을 수행할 대상과 일치하지 않습니다."),
  S3_FILE_UPLOAD_FALSE("파일 업로드에 실패하였습니다."),
  S3_FILE_DELETE_FALSE("파일 삭제에 실패하였습니다.");

  private final String message;

  ErrorMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
