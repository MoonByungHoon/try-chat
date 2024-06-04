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
  public ResponseEntity<MemberResponse> findUserById(@PathVariable final Long userId) {

    return ResponseEntity.ok(memberService.findUserById(userId));
  }

  //  username과 password 변경 요청 처리.
  @PutMapping("/{userId}")
  public ResponseEntity<String> updateUser(
          @PathVariable final Long userId,
          @Valid @RequestBody MemberUpdateRequest memberUpdateRequest
  ) {
    memberService.updateUser(userId, memberUpdateRequest);

    return ResponseEntity.ok("회원정보가 수정되었습니다.");
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> removeUser(
          @PathVariable final Long userId,
          @Valid @RequestBody MemberRemoveRequest memberRemoveRequest
  ) {
    memberService.remove(userId, memberRemoveRequest);

    return ResponseEntity.ok("회원 탈퇴에 성공하였습니다.");
  }

  @GetMapping("/{userId}/profile")
  public ResponseEntity<MemberProfileResponse> findUserProfileByMemberId(@PathVariable final Long userId) {

    return ResponseEntity.ok(memberService.findUserProfileByUserId(userId));
  }

  @GetMapping("/profile")
  public ResponseEntity<MemberProfileResponse> findUserProfileByUsername(@RequestParam final String username) {

    return ResponseEntity.ok(memberService.findUserProfileByUsername(username));
  }

  @PutMapping("/{userId}/profile")
  public ResponseEntity<MemberProfileResponse> updateUserProfile(
          @PathVariable final Long userId,
          @Valid @RequestBody MemberProfileUpdateRequest profileUpdateRequest
  ) {
    return ResponseEntity.ok(memberService.updateUserProfile(userId, profileUpdateRequest));
  }

  @GetMapping("/{userId}/friends")
  public ResponseEntity<List<FriendResponse>> findFriendsByMemberId(@PathVariable final Long userId) {

    return ResponseEntity.ok(friendService.findFriendsByMemberId(userId));
  }
}
