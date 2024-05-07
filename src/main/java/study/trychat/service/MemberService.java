package study.trychat.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberRequest;
import study.trychat.dto.MemberResponse;
import study.trychat.entity.Member;
import study.trychat.entity.MemberInfo;
import study.trychat.exception.custom.CustomPrimaryKeyMismatchException;
import study.trychat.exception.custom.DuplicateUsernameException;
import study.trychat.repository.MemberInfoRepository;
import study.trychat.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
  private static String ENTITY_NOT_FOUND = "일치하는 회원이 없습니다.";
  private static String DUPLICATE_USER = "이미 가입된 회원입니다.";
  private static String PRIMARY_KEY_MISMATCH = "대상이 일치하지 않습니다.";

  private final MemberRepository memberRepository;
  private final MemberInfoRepository memberInfoRepository;

  @Transactional
  public void signUp(MemberAuthenticationDto authenticationDto) {
    checkForDuplicateUsername(authenticationDto.getUsername());

    Member member = authenticationDto.toEntity();

    memberRepository.save(member);
  }

  public MemberRequest signIn(MemberAuthenticationDto authenticationDto) {

    return memberRepository
            .findByUsernameAndPasswordQuerydsl(authenticationDto.getUsername(), authenticationDto.getPassword());
  }

  public MemberAuthenticationDto findUser(Long userId) {

    return memberRepository.findByIdQuerydsl(userId);
  }

  @Transactional
  public void updateUser(Long userId, MemberAuthenticationDto authenticationDto) {

    Member findMember = memberRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

    findMember.update(authenticationDto);
  }

  @Transactional
  public void remove(Long userId, MemberAuthenticationDto authenticationDto) {

    Member findMember =
            memberRepository.findByUsernameAndPassword(authenticationDto.getUsername(), authenticationDto.getPassword());

    compareUserId(userId, findMember.getId());

    memberRepository.delete(findMember);
  }

  public MemberRequest findUserProfile(Long userId) {

    return memberRepository.findByIdForProfileQuerydsl(userId);
  }

  @Transactional
  public MemberRequest updateUserProfile(Long userId, MemberResponse memberResponse) {

    MemberInfo findMemberInfo = memberInfoRepository.findById(memberResponse.getId())
            .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

    compareUserId(userId, findMemberInfo.getId());
    findMemberInfo.update(memberResponse);

    return findMemberInfo.toDto();
  }

  private void checkForDuplicateUsername(String username) {
    if (memberRepository.existsByUsername(username)) {
      throw new DuplicateUsernameException(DUPLICATE_USER);
    }
  }

  private void compareUserId(Long userId, Long findId) {
    if (!(userId.equals(findId))) {
      throw new CustomPrimaryKeyMismatchException(PRIMARY_KEY_MISMATCH);
    }
  }
}
