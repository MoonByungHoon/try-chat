package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class S3UploadException extends BaseCustomExceptions {
  public S3UploadException(ErrorMessage message) {
    super(ErrorMessage.S3_FILE_NOT_FOUND);
  }
}
