package study.trychat.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.trychat.Dto.MemberDto;
import study.trychat.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  public MemberDto signIn(MemberDto memberDto) {
    if (memberRepository.existsByUsername(memberDto.getUsername())) {
      throw new EntityExistsException("중복된 아이디가 있습니다.");
    }

    memberRepository.save(memberDto.toEntity());

    return memberDto;
  }
}
