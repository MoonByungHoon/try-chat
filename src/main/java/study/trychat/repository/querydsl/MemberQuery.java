package study.trychat.repository.querydsl;

import study.trychat.dto.MemberProfileResponse;
import study.trychat.dto.MemberResponse;
import study.trychat.dto.SignInResponse;

public interface MemberQuery {

  SignInResponse findSignInByUsernameAndPassword(String username, String password);

  MemberProfileResponse findMemberProfileById(Long memberId);

  MemberResponse findMemberQueryById(Long memberId);

  MemberProfileResponse findMemberProfileByUsername(String username);
}
