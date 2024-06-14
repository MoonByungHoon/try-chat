package study.trychat.repository.querydsl;

import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberResponse;

public interface MemberQuery {

  MemberResponse findSignInByUsernameAndPassword(String username, String password);

  MemberResponse findProfileById(Long memberId);

  MemberAuthenticationDto findAuthenticationTypeById(Long memberId);

  MemberResponse findUserProfileByUniqueName(String uniqueName);
}
