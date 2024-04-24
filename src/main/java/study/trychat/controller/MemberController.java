package study.trychat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.trychat.Dto.MemberDto;

@RestController
@RequestMapping("auth")
public class MemberController {

  @PostMapping("/sign-up")
  public ResponseEntity<?> signIn(@RequestBody MemberDto memberDto) {

    return ResponseEntity.ok().body(memberDto);
  }
}
