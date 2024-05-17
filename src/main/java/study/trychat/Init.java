package study.trychat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.service.MemberService;

@Component
@Profile("dev")
public class Init implements CommandLineRunner {

  private final MemberService memberService;

  @Autowired
  public Init(MemberService memberService) {
    this.memberService = memberService;
  }

  @Override
  public void run(String... args) throws Exception {
    MemberAuthenticationDto member = new MemberAuthenticationDto("memberTest@test.co.kr", "Testtest@0");
    MemberAuthenticationDto friendTest1 = new MemberAuthenticationDto("friendTest1@test.co.kr", "Testtest@1");
    MemberAuthenticationDto friendTest2 = new MemberAuthenticationDto("friendTest2@test.co.kr", "Testtest@2");

    memberService.signUp(member);
    memberService.signUp(friendTest1);
    memberService.signUp(friendTest2);
  }
}
