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
  public ResponseEntity<MemberResponse> findMemberById(@PathVariable final Long userId) {

    return ResponseEntity.ok(memberService.findMemberById(userId));
  }

  //  username과 password 변경 요청 처리.
  @PutMapping("/{userId}")
  public ResponseEntity<String> updateMember(
          @PathVariable final Long userId,
          @Valid @RequestBody MemberUpdateRequest memberUpdateRequest
  ) {
    memberService.updateMember(userId, memberUpdateRequest);

    return ResponseEntity.ok("회원정보가 수정되었습니다.");
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> removeMember(
          @PathVariable final Long userId,
          @Valid @RequestBody MemberRemoveRequest memberRemoveRequest
  ) {
    memberService.remove(userId, memberRemoveRequest);

    return ResponseEntity.ok("회원 탈퇴에 성공하였습니다.");
  }

  @GetMapping("/{userId}/profile")
  public ResponseEntity<MemberProfileResponse> findMyProfileByMemberId(@PathVariable final Long userId) {

    return ResponseEntity.ok(memberService.findMyProfileByUserId(userId));
  }

  @GetMapping("/profile")
  public ResponseEntity<MemberProfileResponse> findMemberProfileByUsername(@RequestBody final UsernameParam usernameParam) {

    return ResponseEntity.ok(memberService.findMemberProfileByUsername(usernameParam.username()));
  }

  @PutMapping("/{userId}/profile")
  public ResponseEntity<MemberProfileResponse> updateMyProfile(
          @PathVariable final Long userId,
          @Valid @RequestBody MemberProfileUpdateRequest profileUpdateRequest
  ) {
    return ResponseEntity.ok(memberService.updateMemberProfile(userId, profileUpdateRequest));
  }

  @GetMapping("/{userId}/friends")
  public ResponseEntity<List<FriendResponse>> findFriendsByMemberId(@PathVariable final Long userId) {

    return ResponseEntity.ok(friendService.findFriendsByMemberId(userId));
  }
}
