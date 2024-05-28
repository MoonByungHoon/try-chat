package study.trychat.exception.custom;

import study.trychat.exception.ErrorMessage;

public class S3UploadException extends RuntimeException {
  public S3UploadException(ErrorMessage message) {
    super(message.getMessage());
  }
}
