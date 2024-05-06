package study.trychat.repository.querydsl;

import study.trychat.dto.MemberRequest;

public interface MemberQuerydsl {

  MemberRequest findByUsernameAndPasswordQuerydsl(String username, String password);

  MemberRequest findByIdQuerydsl(Long userId);
}
