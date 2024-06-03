package study.trychat;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import study.trychat.dto.SignUpRequest;
import study.trychat.service.MemberService;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class TryChatApplication implements CommandLineRunner {
  private final MemberService memberService;

  public static void main(String[] args) {
    SpringApplication.run(TryChatApplication.class, args);
  }

  @Override
  public void run(String... args) {
    SignUpRequest member = new SignUpRequest("memberTest@test.co.kr", "Testtest@0");
    SignUpRequest friendTest1 = new SignUpRequest("friendTest1@test.co.kr", "Testtest@1");
    SignUpRequest friendTest2 = new SignUpRequest("friendTest2@test.co.kr", "Testtest@2");

    memberService.signUp(member);
    memberService.signUp(friendTest1);
    memberService.signUp(friendTest2);
  }
}
