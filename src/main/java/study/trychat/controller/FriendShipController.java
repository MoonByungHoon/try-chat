package study.trychat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.FriendBase.FriendNicknameUpdateRequest;
import study.trychat.dto.FriendBase.FriendShipResponse;
import study.trychat.dto.MemberBase;
import study.trychat.dto.UsernameParam;
import study.trychat.exception.ApiError;
import study.trychat.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@Tag(name = "친구 관련 API", description = "사용자가 다른 이용자와의 커뮤니케이션을 위해서 추가된 다른 유저들에 대한 처리담당")
public class FriendShipController {

  private final FriendService friendService;

  @Operation(summary = "친구 추가", description = "사용자가 대상의 username을 통해 친구 추가")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "친구 추가 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberBase.MemberProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "친구 추가 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @PostMapping("/add")
  public ResponseEntity<List<FriendShipResponse>> addFriendByUsername(
          @PathVariable
          @Schema(description = "사용자의 ID") final Long userId,
          @Valid @RequestBody UsernameParam usernameParam
  ) {
    return ResponseEntity.ok(friendService.addFriendByUsername(userId, usernameParam.username()));
  }

  @Operation(summary = "친구 프로필 조회", description = "사용자 친구 추가 되어 있는 대상의 프로필 조회")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "조회 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberBase.MemberProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "조회 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{friendId}/profile")
  public ResponseEntity<FriendShipResponse> findFriendByMemberIdAndFriendId(
          @PathVariable
          @Schema(description = "사용자의 ID") final Long userId,
          @PathVariable
          @Schema(description = "친구의 ID") final Long friendId
  ) {
    return ResponseEntity.ok(friendService.findFriendByMemberIdAndFriendId(userId, friendId));
  }

  @Operation(summary = "친구 프로필 수정", description = "친구의 nickname 수정")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "수정 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberBase.MemberProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "수정 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @PatchMapping("/profile")
  public ResponseEntity<FriendShipResponse> updateFriendNickname(
          @PathVariable
          @Schema(description = "사용자의 ID") final Long userId,
          @Valid @RequestBody FriendNicknameUpdateRequest nicknameUpdateRequest
  ) {
    return ResponseEntity.ok(friendService.updateFriendNickname(userId, nicknameUpdateRequest.friendId(), nicknameUpdateRequest.nickname()));
  }

  @Operation(summary = "사용자의 친구 즐겨찾기", description = "즐겨찾기 설정(Best 관계)")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "변경 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberBase.MemberProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "변경 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @PutMapping("/{friendId}/best")
  public ResponseEntity<List<FriendShipResponse>> updateBestFriend(
          @RequestHeader
          @Schema(description = "사용자의 ID") final Long userId,
          @PathVariable
          @Schema(description = "친구의 ID") final Long friendId
  ) {
    return ResponseEntity.ok(friendService.updateBestFriend(userId, friendId));
  }

  @Operation(summary = "사용자의 친구 차단", description = "차단 설정(Block 관계)")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "변경 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberBase.MemberProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "변경 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @PutMapping("/{friendId}/block")
  public ResponseEntity<List<FriendShipResponse>> updateBlockFriend(
          @RequestHeader
          @Schema(description = "사용자의 ID") final Long userId,
          @PathVariable
          @Schema(description = "친구의 ID") final Long friendId
  ) {
    return ResponseEntity.ok(friendService.updateBlockFriend(userId, friendId));
  }

  @Operation(summary = "친구 삭제", description = "사용자 친구 추가 상태인 대상 친구 관계 해제")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "삭제 성공",
                  content = @Content(mediaType = "String",
                          schema = @Schema(implementation = MemberBase.MemberProfileResponse.class))),
          @ApiResponse(responseCode = "400", description = "삭제 실패",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class))),
          @ApiResponse(responseCode = "500", description = "서버 에러",
                  content = @Content(
                          mediaType = "application/json",
                          schema = @Schema(implementation = ApiError.class)))
  })
  @DeleteMapping("/{friendId}")
  public ResponseEntity<List<FriendShipResponse>> removeFriendByMemberIdAndFriendId(
          @RequestHeader
          @Schema(description = "사용자의 ID") final Long userId,
          @PathVariable
          @Schema(description = "친구의 ID") final Long friendId
  ) {
    return ResponseEntity.ok(friendService.removeFriendByMemberIdAndFriendId(userId, friendId));
  }
}
