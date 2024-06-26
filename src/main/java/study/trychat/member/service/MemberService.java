package study.trychat.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import study.trychat.common.exception.custom.DuplicateUsernameException;
import study.trychat.common.exception.custom.FindTargetMismatchException;
import study.trychat.common.exception.custom.MemberNotFoundException;
import study.trychat.infra.S3ImgService;
import study.trychat.member.domain.Member;
import study.trychat.member.domain.MemberInfo;
import study.trychat.member.domain.MemberInfoRepository;
import study.trychat.member.domain.MemberRepository;
import study.trychat.member.dto.MemberBase.MemberProfileResponse;
import study.trychat.member.dto.MemberBase.MemberResponse;
import study.trychat.member.dto.SignBase.SignUpRequest;
import study.trychat.member.dto.SignBase.SingInResponse;
import study.trychat.member.dto.UsernameParam;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static study.trychat.member.dto.MemberBase.MemberProfileUpdateRequest;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberInfoRepository memberInfoRepository;
  private final S3ImgService s3ImgService;

  @Transactional
  public String signUp(SignUpRequest signUpRequest) {

//    가입 이메일 중복 검사.
    checkDuplicateEmail(signUpRequest.email());

//    nickname 추출.
    String extractName = extractByEmail(signUpRequest.email());

//    기본 회원가입 시에 사용될 username 중복 검사 및 중복 시 랜덤한 이름 생성.
    String username = checkDuplicateUsername(extractName);

    Member member = MemberMapper.toMemberEntity(signUpRequest.email(), signUpRequest.password(), extractName, username);

    memberRepository.save(member);

    return "회원가입에 성공하였습니다.";
  }

  // comment : 파라미터가 많지 않은 경우는 그냥 컨트롤러에서 꺼내서 쓴다. 최대한 DTO에 대한 의존성을 줄이는 것은 좋다.
  public SingInResponse signIn(String email,
                               String password) {
    // comment : querydsl을 사용함에도 불구하고 memberRepository에서 감추고 있기 때문에 이게 올바른 리턴인지 전혀 모르겠음.
    // comment : querydsl이 아니여도 충분하게 jpa로 해결할 수 있는데 굳이 querydsl을 쓴다면 jpa를 쓰지말기
    // comment : proejction vs converting

    Member findMember = memberRepository.findByEmailAndPassword(email, password)
            .orElseThrow(MemberNotFoundException::new);

    return MemberMapper.toSingInResponse(findMember);
  }

  public MemberResponse getMember(Long memberId) {

    Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

    return MemberMapper.toMemberResponse(findMember);
  }

  @Transactional
  public String updateMember(Long memberId,
                             String email,
                             String password) {

    memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new)
            .update(email, password);

    return "회원정보 수정이 완료되었습니다.";
  }

  @Transactional
  public String remove(Long memberId, String email, String password) {

    Member findMember = memberRepository.findByEmailAndPassword(email, password)
            .orElseThrow(MemberNotFoundException::new);

    validateIdMatch(memberId, findMember.getId());

    memberRepository.delete(findMember);

    return "회원 탈퇴가 완료되었습니다.";
  }

  public MemberProfileResponse getMyProfile(Long memberId) {

    MemberInfo findMemberInfo = memberInfoRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

    return MemberMapper.toMemberProfileResponse(findMemberInfo);
  }

  public MemberProfileResponse getMemberProfile(String username) {

    MemberInfo findMemberInfo = memberInfoRepository.findByUsername(username)
            .orElseThrow(MemberNotFoundException::new);

    return MemberMapper.toMemberProfileResponse(findMemberInfo);
  }

  @Transactional
  public MemberProfileResponse updateMemberProfile(Long memberId, MemberProfileUpdateRequest profileUpdateRequest,
                                                   Map<String, MultipartFile> files) {

    validateIdMatch(memberId, profileUpdateRequest.id());

    MemberInfo findMemberInfo = memberInfoRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

    if (files != null) {
      return updateAll(findMemberInfo, profileUpdateRequest, files);
    }

    findMemberInfo.update(profileUpdateRequest.nickname(), profileUpdateRequest.greetings());

    return MemberMapper.toMemberProfileResponse(findMemberInfo);
  }

  @Transactional
  public UsernameParam updateMyUsername(Long userId,
                                        UsernameParam usernameParam) {

    memberInfoRepository.findById(userId)
            .orElseThrow(MemberNotFoundException::new)
            .updateUsername(usernameParam.username());

    return usernameParam;
  }

  private MemberProfileResponse updateAll(MemberInfo findMemberInfo,
                                          MemberProfileUpdateRequest profileUpdateRequest,
                                          Map<String, MultipartFile> files) {
    //미작성
    List<String> fileAddress = s3ImgService.upload(files);

    System.out.println("파일주소 : " + fileAddress.get(0));

    findMemberInfo.updateAll(profileUpdateRequest);

    return MemberMapper.toMemberProfileResponse(findMemberInfo);
  }

  private void validateIdMatch(Long memberId,
                               Long id) {
    if (!memberId.equals(id)) {
      throw new FindTargetMismatchException();
    }
  }

  private void checkDuplicateEmail(String email) {
    if (memberRepository.existsByEmail(email)) {
      throw new DuplicateUsernameException();
    }
  }

  private String checkDuplicateUsername(String username) {
    if (memberInfoRepository.existsByUsername(username)) {
      LocalDateTime now = LocalDateTime.now();

      return username + now.getSecond() + now.getNano() / 1000;
    }
    return username;
  }

  private String extractByEmail(String email) {
    return Arrays.stream(email.split("@"))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("email split 배열의 첫번째 요소가 없습니다."));
  }
}
