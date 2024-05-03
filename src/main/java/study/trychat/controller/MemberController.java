package study.trychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.MemberDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody MemberDto memberDto) {

    return ResponseEntity.ok(200);
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@RequestBody MemberDto memberDto) {

    memberDto.setNickName("userTest");

    return ResponseEntity.ok().body(memberDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable("id") final Long id,
                                      @RequestBody MemberDto memberDto) {

    memberDto.setId(id);

    return ResponseEntity.ok().body(memberDto);
  }

  @PutMapping("/profile/user/{id}")
  public ResponseEntity<?> updateUserProfile(@PathVariable("id") final Long id,
                                             @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok().body(memberDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> removeUser(@PathVariable("id") final Long id,
                                      @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok(200);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable("id") final Long id) {

    MemberDto memberDto = new MemberDto("테스트");

    return ResponseEntity.ok().body(memberDto);
  }

  @GetMapping("/profile/user/{id}")
  public ResponseEntity<?> getUserProfile(@PathVariable("id") final Long id) {

    MemberDto memberDto = new MemberDto("userTest", "img", "imgPath");

    return ResponseEntity.ok().body(memberDto);
  }
}
