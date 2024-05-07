package study.trychat.repository.querydsl;

import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberResponse;

public interface MemberQuerydsl {

  MemberResponse findSignInByUsernameAndPassword(String username, String password);

  MemberResponse findProfileById(Long userId);

  MemberAuthenticationDto findAuthenticationTypeById(Long userId);
}
