package study.trychat.repository.querydsl;

import study.trychat.dto.MemberRequest;

public interface MemberQuerydsl {

  MemberRequest findByUsernameAndPasswordSignIn(String username, String password);
}
