package study.trychat.repository.querydsl;

import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberRequest;

public interface MemberQuerydsl {

  MemberRequest findSignInByUsernameAndPassword(String username, String password);

  MemberRequest findProfileById(Long userId);

  MemberAuthenticationDto findAuthenticationTypeById(Long userId);
}
