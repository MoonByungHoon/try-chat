package study.trychat.repository.querydsl;

import study.trychat.dto.MemberProfileResponse;
import study.trychat.dto.MemberResponse;
import study.trychat.dto.SignInResponse;

import java.util.Optional;

public interface MemberQuery {

  SignInResponse findSignInByEmailAndPassword(String email, String password);

  MemberProfileResponse findMyProfileById(Long memberId);

  MemberResponse findMemberQueryById(Long memberId);

  Optional<MemberProfileResponse> findMemberProfileByUsername(String username);
}
