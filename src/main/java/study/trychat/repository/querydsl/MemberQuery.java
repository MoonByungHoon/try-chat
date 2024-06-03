package study.trychat.repository.querydsl;

import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberResponse;
import study.trychat.dto.SignInResponse;

public interface MemberQuery {

  SignInResponse findSignInByUsernameAndPassword(String username, String password);

  MemberResponse findProfileById(Long memberId);

  MemberAuthenticationDto findAuthenticationTypeById(Long memberId);

  MemberResponse findUserProfileByUniqueName(String uniqueName);
}
