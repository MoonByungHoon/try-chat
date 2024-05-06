package study.trychat.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberRequest;
import study.trychat.entity.Member;
import study.trychat.exception.custom.CustomDuplicateUsernameException;
import study.trychat.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
  private static String ENTITY_NOT_FOUND = "일치하는 회원이 없습니다.";
  private static String DUPLICATE_USER = "이미 가입된 회원입니다.";

  private final MemberRepository memberRepository;

  @Transactional
  public void signUp(MemberAuthenticationDto authenticationDto) {
    if (memberRepository.existsByUsername(authenticationDto.getUsername())) {
      throw new CustomDuplicateUsernameException(DUPLICATE_USER);
    }

    Member member = authenticationDto.toEntity();

    memberRepository.save(member);
  }

  public MemberRequest signIn(MemberAuthenticationDto authenticationDto) {

    return memberRepository
            .findByUsernameAndPasswordQuerydsl(authenticationDto.getUsername(), authenticationDto.getPassword());
  }

  public MemberRequest findUser(Long userId) {

    return memberRepository.findByIdQuerydsl(userId);
  }

  public void updateUser(Long userId, MemberAuthenticationDto authenticationDto) {

    Member findMember = memberRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

    findMember.update(authenticationDto);
  }
}
