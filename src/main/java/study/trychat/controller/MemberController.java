package study.trychat.controller;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.trychat.Dto.MemberDto;
import study.trychat.service.MemberService;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/sign-up")
  public ResponseEntity<?> signIn(@RequestBody MemberDto memberDto) {
    try {
      ResponseEntity.ok().body(memberService.signIn(memberDto));

      return ResponseEntity.ok().body(memberDto);
    } catch (EntityExistsException e) {

      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
