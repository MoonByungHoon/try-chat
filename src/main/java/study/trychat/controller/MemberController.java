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
  public ResponseEntity<String> signUp(@RequestBody MemberDto memberDto) {

    return ResponseEntity.ok("회원가입에 성공하였습니다.");
  }

  @PostMapping("/signin")
  public ResponseEntity<MemberDto> signIn(@RequestBody MemberDto memberDto) {

    memberDto.setNickName("userTest");

    return ResponseEntity.ok(memberDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MemberDto> updateUser(@PathVariable("id") final Long id,
                                              @RequestBody MemberDto memberDto) {

    memberDto.setId(id);

    return ResponseEntity.ok(memberDto);
  }

  @PutMapping("/profile/user/{id}")
  public ResponseEntity<MemberDto> updateUserProfile(@PathVariable("id") final Long id,
                                                     @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok(memberDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> removeUser(@PathVariable("id") final Long id,
                                           @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok("회원 탈퇴에 성공하였습니다.");
  }

  @GetMapping("/{id}")
  public ResponseEntity<MemberDto> getUser(@PathVariable("id") final Long id) {

    MemberDto memberDto = new MemberDto("테스트");

    return ResponseEntity.ok(memberDto);
  }

  @GetMapping("/profile/user/{id}")
  public ResponseEntity<MemberDto> getUserProfile(@PathVariable("id") final Long id) {

    MemberDto memberDto = new MemberDto("userTest", "img", "imgPath");

    return ResponseEntity.ok(memberDto);
  }
}
