package study.trychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.FriendDto;
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

  @PutMapping("/{userId}")
  public ResponseEntity<MemberDto> updateUser(@PathVariable("userId") final Long userId,
                                              @RequestBody MemberDto memberDto) {

    memberDto.setId(userId);

    return ResponseEntity.ok(memberDto);
  }

  @PutMapping("/{userId}/profile")
  public ResponseEntity<MemberDto> updateUserProfile(@PathVariable("userId") final Long userId,
                                                     @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok(memberDto);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> removeUser(@PathVariable("userId") final Long userId,
                                           @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok("회원 탈퇴에 성공하였습니다.");
  }

  @GetMapping("/{userId}")
  public ResponseEntity<MemberDto> getUser(@PathVariable("userId") final Long userId) {

    MemberDto memberDto = new MemberDto("테스트");

    return ResponseEntity.ok(memberDto);
  }

  @GetMapping("/{userId}/profile")
  public ResponseEntity<MemberDto> getUserProfile(@PathVariable("userId") final Long userId) {

    MemberDto memberDto = new MemberDto("userTest", "img", "imgPath");

    return ResponseEntity.ok(memberDto);
  }

  @GetMapping("/{userId}/friends")
  public ResponseEntity<MemberDto> findFriendByUserId(@PathVariable("userId") final Long userId) {
    MemberDto memberDto = new MemberDto(userId, "testUser",
            new FriendDto("testFriend1"), new FriendDto("testFriend2"));

    return ResponseEntity.ok(memberDto);
  }
}
