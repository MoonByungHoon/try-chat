package study.trychat.exception.custom;

import study.trychat.message.ErrorMessage;

public class S3UploadException extends RuntimeException {
  public S3UploadException(ErrorMessage message) {
    super(message.getMessage());
  }
}
