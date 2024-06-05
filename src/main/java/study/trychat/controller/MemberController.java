package study.trychat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.trychat.dto.*;
import study.trychat.service.FriendService;
import study.trychat.service.MemberService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final FriendService friendService;

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

    return ResponseEntity.ok(memberService.signUp(signUpRequest));
  }

  @PostMapping("/signin")
  public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {

    return ResponseEntity.ok(memberService.signIn(signInRequest.email(), signInRequest.password()));
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

    return ResponseEntity.ok(memberService.updateMember(userId, memberUpdateRequest.email(), memberUpdateRequest.password()));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> removeMember(
          @PathVariable final Long userId,
          @Valid @RequestBody MemberRemoveRequest memberRemoveRequest
  ) {
    memberService.remove(userId, memberRemoveRequest.email(), memberRemoveRequest.password());

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
          @Valid @RequestBody MemberProfileUpdateRequest profileUpdateRequest,
          @RequestPart(value = "files", required = false) Map<String, MultipartFile> files
  ) {
    return ResponseEntity.ok(memberService.updateMemberProfile(userId, profileUpdateRequest, files));
  }

  @GetMapping("/{userId}/friends")
  public ResponseEntity<List<FriendResponse>> findFriendsByMemberId(@PathVariable final Long userId) {

    return ResponseEntity.ok(friendService.findFriendsByMemberId(userId));
  }

  @PatchMapping("/username")
  public ResponseEntity<UsernameParam> updateMyUsername(
          @RequestHeader final Long userId,
          @RequestBody UsernameParam usernameParam
  ) {

    return ResponseEntity.ok(memberService.updateMyUsername(userId, usernameParam));
  }
}
