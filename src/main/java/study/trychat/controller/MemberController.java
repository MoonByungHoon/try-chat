package study.trychat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.*;
import study.trychat.service.FriendService;
import study.trychat.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final FriendService friendService;

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

    memberService.signUp(signUpRequest);

    return ResponseEntity.ok("회원가입에 성공하였습니다.");
  }

  @PostMapping("/signin")
  public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {

    return ResponseEntity.ok(memberService.signIn(signInRequest));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<MemberAuthenticationDto> findUserById(@PathVariable final Long userId) {

    return ResponseEntity.ok(memberService.findUser(userId));
  }

  //  username과 password 변경 요청 처리.
  @PutMapping("/{userId}")
  public ResponseEntity<String> updateUser(@PathVariable final Long userId,
                                           @Valid @RequestBody MemberAuthenticationDto authenticationDto) {

    memberService.updateUser(userId, authenticationDto);

    return ResponseEntity.ok("회원정보가 수정되었습니다.");
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> removeUser(@PathVariable final Long userId,
                                           @Valid @RequestBody MemberAuthenticationDto authenticationDto) {

    memberService.remove(userId, authenticationDto);

    return ResponseEntity.ok("회원 탈퇴에 성공하였습니다.");
  }

  @GetMapping("/{userId}/profile")
  public ResponseEntity<MemberResponse> findUserProfileByMemberId(@PathVariable final Long userId) {

    return ResponseEntity.ok(memberService.findUserProfileByUserId(userId));
  }

  @GetMapping("/profile")
  public ResponseEntity<MemberResponse> findUserProfileByUniqueName(@RequestParam final String uniqueName) {

    return ResponseEntity.ok(memberService.findUserProfileByUniqueName(uniqueName));
  }

  @PutMapping("/{userId}/profile")
  public ResponseEntity<MemberResponse> updateUserProfile(@PathVariable final Long userId,
                                                          @Valid @RequestBody MemberRequest memberRequest) {

    return ResponseEntity.ok(memberService.updateUserProfile(userId, memberRequest));
  }

  @GetMapping("/{userId}/friends")
  public ResponseEntity<List<FriendResponse>> findFriendsByMemberId(@PathVariable final Long userId) {

    return ResponseEntity.ok(friendService.findByMemberId(userId));
  }
}
