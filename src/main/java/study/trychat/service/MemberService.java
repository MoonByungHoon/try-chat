package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.*;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.custom.DuplicateUsernameException;
import study.trychat.exception.custom.EntityNotFoundException;
import study.trychat.repository.MemberInfoRepository;
import study.trychat.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberInfoRepository memberInfoRepository;

  @Transactional
  public void signUp(SignUpRequest signUpRequest) {
//    가입 이메일 중복 검사.
    checkDuplicateEmail(signUpRequest.email());

//    검색 필터에 사용될 유니크 네임 추출.
    String extractName = extractByEmail(signUpRequest.email());

//    유니크 네임 중복 검사 및 중복 시 랜덤한 이름 생성.
    String uniqueName = checkDuplicateUniqueName(extractName);

    Member member = signUpRequest.toEntityForSignUp(uniqueName);

    memberRepository.save(member);
  }

  public SignInResponse signIn(SignInRequest signInRequest) {

    SignInResponse findMember = memberRepository
            .findSignInByUsernameAndPassword(signInRequest.email(), signInRequest.password());

    if (findMember == null) {
      throw new EntityNotFoundException();
    }

    return findMember;
  }

  public MemberResponse findUserById(Long memberId) {

    return memberRepository.findUserQueryById(memberId);
  }

  @Transactional
  public void updateUser(Long memberId, MemberUpdateRequest memberUpdateRequest) {

    if (memberRepository.existsByEmail(memberUpdateRequest.email())) {
      throw new DuplicateUsernameException();
    }

    Member findMember = memberRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException());

    findMember.update(memberUpdateRequest);
  }

  @Transactional
  public void remove(Long memberId, MemberRemoveRequest memberRemoveRequest) {

    Member findMember =
            memberRepository.findByEmailAndPassword(memberRemoveRequest.email(), memberRemoveRequest.password());

    findMember.checkId(memberId);

    memberRepository.delete(findMember);
  }

  public MemberProfileResponse findUserProfileByUserId(Long memberId) {

    return memberRepository.findUserProfileById(memberId);
  }

  public MemberProfileResponse findUserProfileByUsername(String username) {

    return memberRepository.findUserProfileByUsername(username);
  }

  @Transactional
  public MemberProfileResponse updateUserProfile(Long memberId, MemberProfileUpdateRequest profileUpdateRequest) {

    MemberInfo findMemberInfo = memberInfoRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException());

    findMemberInfo.checkId(memberId);
    findMemberInfo.update(profileUpdateRequest);

    return MemberProfileResponse.change(findMemberInfo);
  }

  private void checkDuplicateEmail(String email) {
    if (memberRepository.existsByEmail(email)) {
      throw new DuplicateUsernameException();
    }
  }

  private String checkDuplicateUniqueName(String uniqueName) {
    if (memberInfoRepository.existsByUsername(uniqueName)) {

      LocalDateTime now = LocalDateTime.now();

      String makeUniqueName = uniqueName + String.valueOf(now.getSecond() + now.getNano() / 1000);

      return makeUniqueName;
    }
    return uniqueName;
  }

  private String extractByEmail(String email) {
    return Arrays.stream(email.split("@"))
            .findFirst().orElseThrow(() -> new NoSuchElementException("email split 배열의 첫번째 요소가 없습니다."));
  }
}
