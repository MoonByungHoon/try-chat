package study.trychat.common.exception.custom;

import study.trychat.common.exception.ErrorMessage;

public class S3UploadException extends RuntimeException {
  public S3UploadException(ErrorMessage message) {
    super(message.getMessage());
  }
}
