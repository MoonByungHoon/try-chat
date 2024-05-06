package study.trychat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.FriendDto;
import study.trychat.dto.MemberAuthenticationDto;
import study.trychat.dto.MemberDto;
import study.trychat.dto.MemberRequest;
import study.trychat.service.MemberService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@Valid @RequestBody MemberAuthenticationDto authenticationDto) {

    authenticationDto.validateUsername(authenticationDto);

    memberService.signUp(authenticationDto);

    return ResponseEntity.ok("회원가입에 성공하였습니다.");
  }

  @PostMapping("/signin")
  public ResponseEntity<MemberRequest> signIn(@Valid @RequestBody MemberAuthenticationDto authenticationDto) {


    return ResponseEntity.ok(memberService.signIn(authenticationDto));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<MemberRequest> findUser(@PathVariable("userId") final Long userId) {

    return ResponseEntity.ok(memberService.findUser(userId));
  }

  //  username과 password 변경 요청 처리.
  @PutMapping("/{userId}")
  public ResponseEntity<String> updateUser(@PathVariable("userId") final Long userId,
                                           @RequestBody MemberAuthenticationDto authenticationDto) {

    memberService.updateUser(userId, authenticationDto);

    return ResponseEntity.ok("회원정보가 수정되었습니다.");
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> removeUser(@PathVariable("userId") final Long userId,
                                           @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok("회원 탈퇴에 성공하였습니다.");
  }

  @GetMapping("/{userId}/profile")
  public ResponseEntity<MemberDto> getUserProfile(@PathVariable("userId") final Long userId) {

    MemberDto memberDto = new MemberDto("userTest", "img", "imgPath");

    return ResponseEntity.ok(memberDto);
  }

  @PutMapping("/{userId}/profile")
  public ResponseEntity<MemberDto> updateUserProfile(@PathVariable("userId") final Long userId,
                                                     @RequestBody MemberDto memberDto) {

    return ResponseEntity.ok(memberDto);
  }

  @GetMapping("/{userId}/friends")
  public ResponseEntity<MemberDto> findFriendsByUserId(@PathVariable("userId") final Long userId) {
    MemberDto memberDto = new MemberDto(userId, "testUser",
            new FriendDto("testFriend1"), new FriendDto("testFriend2"));

    return ResponseEntity.ok(memberDto);
  }
}
