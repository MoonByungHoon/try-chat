package study.trychat.repository.querydsl;

import study.trychat.dto.MemberProfileResponse;
import study.trychat.dto.MemberResponse;
import study.trychat.dto.SignInResponse;

public interface MemberQuery {

  SignInResponse findSignInByUsernameAndPassword(String username, String password);

  MemberProfileResponse findUserProfileById(Long memberId);

  MemberResponse findUserQueryById(Long memberId);

  MemberProfileResponse findUserProfileByUsername(String username);
}
