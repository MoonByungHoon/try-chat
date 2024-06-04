package study.trychat.dto;

import com.querydsl.core.annotations.QueryProjection;

public record MemberResponse(
        Long id,
        String email
) {
  @QueryProjection
  public MemberResponse(Long id, String email) {
    this.id = id;
    this.email = email;
  }
}
