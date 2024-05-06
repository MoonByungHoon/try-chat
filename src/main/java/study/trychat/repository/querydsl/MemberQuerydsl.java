package study.trychat.repository.querydsl;

import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberRequest;

public interface MemberQuerydsl {

  MemberRequest findByUsernameAndPasswordQuerydsl(String username, String password);

  MemberAuthenticationDto findByIdQuerydsl(Long userId);

  MemberRequest findByIdForProfileQuerydsl(Long userId);
}
