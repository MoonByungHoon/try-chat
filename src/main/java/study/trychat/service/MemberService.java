package study.trychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberRequest;
import study.trychat.dto.MemberResponse;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.custom.DuplicateUsernameException;
import study.trychat.exception.custom.EntityNotFoundException;
import study.trychat.repository.MemberInfoRepository;
import study.trychat.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberInfoRepository memberInfoRepository;

  @Transactional
  public void signUp(MemberAuthenticationDto authenticationDto) {
    checkDuplicateUsername(authenticationDto.getUsername());

    Member member = authenticationDto.toEntity();

    memberRepository.save(member);
  }

  public MemberResponse signIn(MemberAuthenticationDto authenticationDto) {

    return memberRepository
            .findSignInByUsernameAndPassword(authenticationDto.getUsername(), authenticationDto.getPassword());
  }

  public MemberAuthenticationDto findUser(Long userId) {

    return memberRepository.findAuthenticationTypeById(userId);
  }

  @Transactional
  public void updateUser(Long userId, MemberAuthenticationDto authenticationDto) {

    if (memberRepository.existsByUsername(authenticationDto.getUsername())) {
      throw new DuplicateUsernameException();
    }

    Member findMember = memberRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException());

    findMember.update(authenticationDto);
  }

  @Transactional
  public void remove(Long userId, MemberAuthenticationDto authenticationDto) {

    Member findMember =
            memberRepository.findByUsernameAndPassword(authenticationDto.getUsername(), authenticationDto.getPassword());

    findMember.checkId(userId);

    memberRepository.delete(findMember);
  }

  public MemberResponse findUserProfile(Long userId) {

    return memberRepository.findProfileById(userId);
  }

  @Transactional
  public MemberResponse updateUserProfile(Long userId, MemberRequest memberRequest) {

    MemberInfo findMemberInfo = memberInfoRepository.findById(memberRequest.getId())
            .orElseThrow(() -> new EntityNotFoundException());

    findMemberInfo.checkId(userId);
    findMemberInfo.update(memberRequest);

    return MemberResponse.fromRequest(memberRequest);
  }

  private void checkDuplicateUsername(String username) {
    if (memberRepository.existsByUsername(username)) {
      throw new DuplicateUsernameException();
    }
  }
}
