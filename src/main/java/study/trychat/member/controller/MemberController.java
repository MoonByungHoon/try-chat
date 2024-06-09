package study.trychat.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.trychat.friend.dto.FriendBase.FriendShipResponse;
import study.trychat.member.dto.MemberBase;
import study.trychat.member.dto.SignBase;
import study.trychat.member.dto.SignBase.SignInRequest;
import study.trychat.member.dto.SignBase.SignUpRequest;
import study.trychat.member.dto.SignBase.SingInResponse;
import study.trychat.member.dto.UsernameParam;
import study.trychat.common.exception.ApiError;
import study.trychat.friend.service.FriendService;
import study.trychat.member.service.MemberService;

import java.util.List;
import java.util.Map;

import static study.trychat.member.dto.MemberBase.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원과 직접적으로 관련된 요청을 처리하는 API")
public class MemberController {

  private final MemberService memberService;
  private final FriendService friendService;

  @PostMapping("/signup")
  @Operation(summary = "회원가입", description = "사용자가 회원가입")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "회원가입 성공",
                  content = @Content(mediaType = "String")),
          @ApiResponse(responseCode = "400", description = "회원가입 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {

    return ResponseEntity.ok(memberService.signUp(signUpRequest));
  }

  @Operation(summary = "로그인", description = "사용자 로그인")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "로그인 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = SignBase.SignUpRequest.class))),
          @ApiResponse(responseCode = "400", description = "로그인 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @PostMapping("/signin")
  public ResponseEntity<SingInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {

    return ResponseEntity.ok(memberService.signIn(signInRequest.email(), signInRequest.password()));
  }

  @Operation(summary = "가입 정보 조회", description = "사용자의 가입 정보를 조회")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "조회 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberBase.MemberResponse.class))),
          @ApiResponse(responseCode = "400", description = "조회 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{userId}")
  public ResponseEntity<MemberResponse> getMember(
          @PathVariable
          @Schema(description = "사용자의 ID") final Long userId
  ) {
    return ResponseEntity.ok(memberService.getMember(userId));
  }

  @Operation(summary = "가입 정보 변경", description = "사용자의 가입 정보를 변경")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "변경 성공",
                  content = @Content(mediaType = "String")),
          @ApiResponse(responseCode = "400", description = "변경 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @PutMapping("/{userId}")
  public ResponseEntity<String> updateMember(
          @PathVariable
          @Schema(description = "사용자의 ID") final Long userId,
          @Valid @RequestBody MemberUpdateRequest memberUpdateRequest
  ) {
    return ResponseEntity.ok(memberService.updateMember(userId, memberUpdateRequest.email(), memberUpdateRequest.password()));
  }

  @Operation(summary = "회원 탈퇴", description = "사용자 탈퇴")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "탈퇴 성공",
                  content = @Content(mediaType = "String")),
          @ApiResponse(responseCode = "400", description = "탈퇴 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @DeleteMapping("/{userId}")
  public ResponseEntity<String> removeMember(
          @PathVariable
          @Schema(description = "사용자의 ID") final Long userId,
          @Valid @RequestBody MemberRemoveRequest memberRemoveRequest
  ) {
    return ResponseEntity.ok(memberService.remove(userId, memberRemoveRequest.email(), memberRemoveRequest.password()));
  }

  @Operation(summary = "유저 프로필 조회", description = "지정 대상의 프로필을 조회")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "조회 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "조회 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{userId}/profile")
  public ResponseEntity<MemberProfileResponse> getMyProfile(
          @PathVariable
          @Schema(description = "사용자의 ID") final Long userId
  ) {
    return ResponseEntity.ok(memberService.getMyProfile(userId));
  }

  @Operation(summary = "유저 프로필 조회", description = "유저의 프로필을 username으로 조회")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "조회 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "조회 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/profile")
  public ResponseEntity<MemberProfileResponse> getMemberProfile(@RequestBody final UsernameParam usernameParam) {

    return ResponseEntity.ok(memberService.getMemberProfile(usernameParam.username()));
  }

  @Operation(summary = "사용자 본인의 프로필 수정", description = "사용자 본인 프로필만 수정 요청 가능")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "수정 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "수정 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @PutMapping(value = "/{userId}/profile", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MemberProfileResponse> updateMyProfile(
          @PathVariable
          @Schema(description = "사용자의 ID") final Long userId,
          @Valid @RequestBody MemberProfileUpdateRequest profileUpdateRequest,
          @RequestPart(value = "files", required = false) Map<String, MultipartFile> files
  ) {

//    todo: 복수 파일에 대한 swagger 처리 방법 적용해야함.

    return ResponseEntity.ok(memberService.updateMemberProfile(userId, profileUpdateRequest, files));
  }

  @Operation(summary = "친구 리스트 조회", description = "사용자가 추가해둔 친구 리스트 조회")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "조회 성공",
                  content = @Content(mediaType = "application/json",
                          array = @ArraySchema(schema = @Schema(implementation = FriendShipResponse.class)))),
          @ApiResponse(responseCode = "400", description = "조회 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{userId}/friends")
  public ResponseEntity<List<FriendShipResponse>> getFriendList(
          @PathVariable
          @Schema(description = "로그인된 사용자 ID, 추후 JWT로 변경 예정") final Long userId) {

    return ResponseEntity.ok(friendService.getFriendList(userId));
  }

  @Operation(summary = "사용자 username 변경", description = "친구 검색에 사용되는 username 변경")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "변경 성공",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = UsernameParam.class))),
          @ApiResponse(responseCode = "400", description = "변경 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @PatchMapping("/{userId}")
  public ResponseEntity<UsernameParam> updateMyUsername(
          @PathVariable
          @Schema(description = "로그인 된 사용자 ID, 추후 JWT 변경 예정") final Long userId,
          @RequestBody UsernameParam usernameParam
  ) {
    return ResponseEntity.ok(memberService.updateMyUsername(userId, usernameParam));
  }
}
