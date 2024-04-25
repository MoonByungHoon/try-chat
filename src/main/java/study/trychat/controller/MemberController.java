package study.trychat.controller;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.Dto.MemberDto;
import study.trychat.service.MemberService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody MemberDto memberDto) {
    try {
      ResponseEntity.ok().body(memberService.signIn(memberDto));

      return ResponseEntity.ok().body(memberDto);
    } catch (EntityExistsException e) {

      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody MemberDto memberDto) {

    return ResponseEntity.ok(200);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable("id") final Long id,
                                      @RequestBody MemberDto memberDto) {

    memberDto.setId(id);

    return ResponseEntity.ok().body(memberDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable("id") final Long id,
                                      @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok(200);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable("id") final Long id) {

    MemberDto memberDto = new MemberDto("테스트");

    return ResponseEntity.ok().body(memberDto);
  }
}
