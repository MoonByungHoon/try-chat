package study.trychat.common.exception;

public record ErrorResponse(
        String message,
        int status
) {
}
