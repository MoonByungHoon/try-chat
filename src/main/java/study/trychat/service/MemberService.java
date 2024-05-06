package study.trychat.service;

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

  private final MemberRepository memberRepository;

  @Transactional
  public void signUp(MemberAuthenticationDto authenticationDto) {
    if (memberRepository.existsByUsername(authenticationDto.getUsername())) {
      throw new CustomDuplicateUsernameException("이미 가입된 회원입니다.");
    }

    Member member = authenticationDto.toEntity();

    memberRepository.save(member);
  }

  public MemberRequest signIn(MemberAuthenticationDto authenticationDto) {

    return memberRepository
            .findByUsernameAndPasswordSignIn(authenticationDto.getUsername(), authenticationDto.getPassword());
  }
}
