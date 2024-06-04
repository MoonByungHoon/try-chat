package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import study.trychat.dto.*;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.custom.DuplicateUsernameException;
import study.trychat.exception.custom.EntityNotFoundException;
import study.trychat.exception.custom.PrimaryKeyMismatchException;
import study.trychat.repository.MemberInfoRepository;
import study.trychat.repository.MemberRepository;
import study.trychat.s3.S3ImgService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberInfoRepository memberInfoRepository;
  private final S3ImgService s3ImgService;

  @Transactional
  public void signUp(SignUpRequest signUpRequest) {
//    가입 이메일 중복 검사.
    checkDuplicateEmail(signUpRequest.email());

//    검색 필터에 사용될 username 추출.
    String extractName = extractByEmail(signUpRequest.email());

//    username 중복 검사 및 중복 시 랜덤한 이름 생성.
    String uniqueName = checkDuplicateUniqueName(extractName);

    Member member = signUpRequest.toEntityForSignUp(uniqueName);

    memberRepository.save(member);
  }

  public SignInResponse signIn(SignInRequest signInRequest) {

    SignInResponse findMember = memberRepository
            .findSignInByUsernameAndPassword(signInRequest.email(), signInRequest.password());

    validateFindMember(findMember);

    return findMember;
  }

  public MemberResponse findMemberById(Long memberId) {

    MemberResponse findMember = memberRepository.findMemberQueryById(memberId);

    validateFindMember(findMember);

    return findMember;
  }

  @Transactional
  public void updateMember(Long memberId, MemberUpdateRequest memberUpdateRequest) {

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
            memberRepository.findByEmailAndPassword(
                    memberRemoveRequest.email(),
                    memberRemoveRequest.password()
            );

    findMember.checkId(memberId);

    memberRepository.delete(findMember);
  }

  public MemberProfileResponse findMyProfileByUserId(Long memberId) {

    MemberProfileResponse findMyProfile = memberRepository.findMyProfileById(memberId);

    validateFindMember(findMyProfile);

    return findMyProfile;
  }

  public MemberProfileResponse findMemberProfileByUsername(String username) {

    return memberRepository.findMemberProfileByUsername(username);
  }

  @Transactional
  public MemberProfileResponse updateMemberProfile(Long memberId,
                                                   MemberProfileUpdateRequest profileUpdateRequest,
                                                   Map<String, MultipartFile> files) {

    validateIdMatch(memberId, profileUpdateRequest.id());

    MemberInfo findMemberInfo = memberInfoRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException());

    if (isPresentFiles(files)) {
      findMemberInfo.update(profileUpdateRequest);

      return MemberProfileResponse.change(findMemberInfo);
    }

    List<String> fileAddress = s3ImgService.upload(files);

    System.out.println("파일주소 : " + fileAddress.get(0));

//    이미지 경로 수정 코드 추가 작성 필요.

    return MemberProfileResponse.change(findMemberInfo);
  }

  private boolean isPresentFiles(Map<String, MultipartFile> files) {
    if (files.isEmpty()) {
      return false;
    }
    return true;
  }

  private void validateFileAddress(
          MemberInfo findMemberInfo,
          MemberProfileUpdateRequest profileUpdateRequest,
          List<String> fileAddress
  ) {
    if (!fileAddress.equals(null)) {
      System.out.println(fileAddress.get(0));

      findMemberInfo.update(profileUpdateRequest);
    }
  }

  private void validateIdMatch(Long memberId, Long id) {
    if (!memberId.equals(id)) {
      throw new PrimaryKeyMismatchException();
    }
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

  private void validateFindMember(MemberProfileResponse findMember) {
    if (findMember.equals(null)) {
      throw new EntityNotFoundException();
    }
  }

  private void validateFindMember(MemberResponse findMember) {
    if (findMember.equals(null)) {
      throw new EntityNotFoundException();
    }
  }

  private void validateFindMember(SignInResponse findMember) {
    if (findMember.equals(null)) {
      throw new EntityNotFoundException();
    }
  }
}
