package study.trychat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.FriendBase.FriendNicknameUpdateRequest;
import study.trychat.dto.FriendBase.FriendResponse;
import study.trychat.dto.UsernameParam;
import study.trychat.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

  private final FriendService friendService;

  //  유니크 네임으로 검색 친구 추가.
  @PostMapping("/add")
  public ResponseEntity<List<FriendResponse>> addFriendByNickname(
          @RequestHeader final Long userId,
          @Valid @RequestBody UsernameParam usernameParam
  ) {
    return ResponseEntity.ok(friendService.addFriendByUsername(userId, usernameParam.username()));
  }

  @GetMapping("/{friendId}/profile")
  public ResponseEntity<FriendResponse> findFriendsByMemberIdAndFriendId(
          @RequestHeader final Long userId,
          @PathVariable final Long friendId
  ) {
    return ResponseEntity.ok(friendService.findFriendsByMemberIdAndFriendId(userId, friendId));
  }

  @PatchMapping("/profile")
  public ResponseEntity<FriendResponse> updateFriendNickname(
          @RequestHeader final Long userId,
          @Valid @RequestBody FriendNicknameUpdateRequest nicknameUpdateRequest
  ) {
    return ResponseEntity.ok(friendService.updateFriendNickname(userId, nicknameUpdateRequest.friendId(), nicknameUpdateRequest.nickname()));
  }

  // PUT /friends/status -> status의 개수만큼의 책임을 감당한다.
  // PUT /friends/best
  // PUT /friends/1/block
  @PutMapping("/{friendId}/best")
  public ResponseEntity<List<FriendResponse>> updateBestFriend(
          @RequestHeader final Long userId,
          @PathVariable final Long friendId
  ) {
    return ResponseEntity.ok(friendService.updateBestFriend(userId, friendId));
  }

  @PutMapping("/{friendId}/block")
  public ResponseEntity<List<FriendResponse>> updateBlockFriend(
          @RequestHeader final Long userId,
          @PathVariable final Long friendId
  ) {
    return ResponseEntity.ok(friendService.updateBlockFriend(userId, friendId));
  }

  @DeleteMapping("/{friendId}")
  public ResponseEntity<List<FriendResponse>> removeFriendByMemberIdAndFriendId(
          @RequestHeader final Long userId,
          @PathVariable final Long friendId
  ) {
    return ResponseEntity.ok(friendService.removeFriendByMemberIdAndFriendId(userId, friendId));
  }
}
