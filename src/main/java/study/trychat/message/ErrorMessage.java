package study.trychat.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
  FRIEND_DELETE_FALSE("친구 삭제에 실패하였습니다."),
  FRIEND_ADD_DUPLICATE_USER_NAME("이미 친구 추가가 되어있습니다."),
  FRIEND_NO_SUCH("대상은 현재 친구가 아닙니다."),
  FRIEND_BEST_SIZE_MAX("즐겨찾기를 등록할 수 있는 친구 수를 초과하였습니다."),
  SIGN_UP_DUPLICATE_USER("이미 가입된 회원입니다."),
  MEMBER_ENTITY_NOT_FOUND("일치하는 회원을 찾지 못했습니다."),
  PRIMARY_KEY_MISMATCH("사용자와 대상이 일치하지 않습니다."),
  S3_FILE_NOT_FOUND("파일 형식이 잘못되었습니다."),
  S3_FILE_IS_NULL("파일이 존재하지 않습니다."),
  S3_FILE_UPLOAD_FALSE("파일 업로드에 실패하였습니다."),
  S3_FILE_DELETE_FALSE("파일 삭제에 실패하였습니다.");

  private final String message;
}
