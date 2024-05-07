package study.trychat.repository.querydsl;

import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberRequestt;

public interface MemberQuerydsl {

  MemberRequestt findSignInByUsernameAndPassword(String username, String password);

  MemberRequestt findProfileById(Long userId);

  MemberAuthenticationDto findAuthenticationTypeById(Long userId);
}
