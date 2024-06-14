package study.trychat.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
  FRIEND_DELETE_FALSE("친구 삭제에 실패하였습니다.", HttpStatus.BAD_REQUEST),
  FRIEND_ADD_DUPLICATE_USER_NAME("이미 친구 추가가 되어있습니다.", HttpStatus.CONFLICT),
  FRIEND_NO_SUCH("대상은 현재 친구가 아닙니다.", HttpStatus.BAD_REQUEST),
  FRIEND_BEST_SIZE_MAX("즐겨찾기를 등록할 수 있는 친구 수를 초과하였습니다.", HttpStatus.BAD_REQUEST),
  SIGN_UP_DUPLICATE_USER("이미 가입된 회원입니다.", HttpStatus.CONFLICT),
  MEMBER_ENTITY_NOT_FOUND("일치하는 회원을 찾지 못했습니다.", HttpStatus.BAD_REQUEST),
  FIND_TARGET_MISMATCH("사용자와 대상이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
  FRIEND_NOW_BEST("이미 대상이 BEST 상태입니다.", HttpStatus.CONFLICT),
  FRIEND_NOW_BLOCK("이미 대상이 BLOCK 상태입니다.", HttpStatus.CONFLICT),
  S3_FILE_NOT_FOUND("파일 형식이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
  S3_FILE_IS_NULL("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
  S3_FILE_UPLOAD_FALSE("파일 업로드에 실패하였습니다.", HttpStatus.BAD_REQUEST),
  S3_FILE_DELETE_FALSE("파일 삭제에 실패하였습니다.", HttpStatus.BAD_REQUEST),
  FRIEND_ADD_TARGET_ME("친구 추가 대상이 본인입니다.", HttpStatus.CONFLICT),
  CHATROOM_ENTITY_NOT_FOUND("채팅방을 찾을 수 없습니다.", HttpStatus.CONFLICT),
  CHATROOM_NOT_HOST("해당 채팅방의 수정 권한이 없습니다.", HttpStatus.BAD_REQUEST),
  NOT_ROOM_MEMBER("해당 채팅방의 맴버가 아닙니다.", HttpStatus.CONFLICT);

  private final String message;
  private final HttpStatus status;
}
