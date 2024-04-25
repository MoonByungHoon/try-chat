package study.trychat.controller;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.Dto.MemberDto;
import study.trychat.service.MemberService;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody MemberDto memberDto) {
    try {
      ResponseEntity.ok().body(memberService.signIn(memberDto));

      return ResponseEntity.ok().body(memberDto);
    } catch (EntityExistsException e) {

      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody MemberDto memberDto) {

    return ResponseEntity.ok(200);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@PathVariable("id") Long id,
                                  @RequestBody MemberDto memberDto) {

    memberDto.setId(id);

    return ResponseEntity.ok().body(memberDto);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                  @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok(200);
  }
}
