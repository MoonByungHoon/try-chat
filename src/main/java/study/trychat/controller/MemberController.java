package study.trychat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.*;
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
  public ResponseEntity<MemberResponse> signIn(@Valid @RequestBody MemberAuthenticationDto authenticationDto) {


    return ResponseEntity.ok(memberService.signIn(authenticationDto));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<MemberAuthenticationDto> findUser(@PathVariable("userId") final Long userId) {

    return ResponseEntity.ok(memberService.findUser(userId));
  }

  //  username과 password 변경 요청 처리.
  @PutMapping("/{userId}")
  public ResponseEntity<String> updateUser(@PathVariable("userId") final Long userId,
                                           @Valid @RequestBody MemberAuthenticationDto authenticationDto) {

    memberService.updateUser(userId, authenticationDto);

    return ResponseEntity.ok("회원정보가 수정되었습니다.");
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> removeUser(@PathVariable("userId") final Long userId,
                                           @Valid @RequestBody MemberAuthenticationDto authenticationDto) {

    memberService.remove(userId, authenticationDto);

    return ResponseEntity.ok("회원 탈퇴에 성공하였습니다.");
  }

  @GetMapping("/{userId}/profile")
  public ResponseEntity<MemberResponse> findUserProfile(@PathVariable("userId") final Long userId) {

    return ResponseEntity.ok(memberService.findUserProfile(userId));
  }

  @PutMapping("/{userId}/profile")
  public ResponseEntity<MemberResponse> updateUserProfile(@PathVariable("userId") final Long userId,
                                                          @Valid @RequestBody MemberRequest memberRequest) {

    return ResponseEntity.ok(memberService.updateUserProfile(userId, memberRequest));
  }

  @GetMapping("/{userId}/friends")
  public ResponseEntity<MemberDto> findFriendsByUserId(@PathVariable("userId") final Long userId) {
    MemberDto memberDto = new MemberDto(userId, "testUser",
            new FriendDto("testFriend1"), new FriendDto("testFriend2"));

    return ResponseEntity.ok(memberDto);
  }
}
