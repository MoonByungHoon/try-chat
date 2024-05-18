package study.trychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.FriendRequest;
import study.trychat.dto.FriendResponse;
import study.trychat.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

  private final FriendService friendService;

  //  유니크 네임으로 검색 친구 추가.
  @PostMapping("/{uniqueName}")
  public ResponseEntity<List<FriendResponse>> addFriendByNickname(@RequestHeader final Long userId,
                                                                  @PathVariable final String uniqueName) {

    return ResponseEntity.ok(friendService.addFriendByNickname(userId, uniqueName));
  }

  @GetMapping("/{friendId}/profile")
  public ResponseEntity<FriendResponse> findFriendByFriendId(@RequestHeader final Long userId,
                                                             @PathVariable final Long friendId) {


    return ResponseEntity.ok(friendService.findFriendByFriendId(userId, friendId));
  }

  @PutMapping("/profile")
  public ResponseEntity<FriendResponse> updateFriendProfile(@RequestHeader final Long userId,
                                                            @RequestBody FriendRequest friendRequest) {

    return ResponseEntity.ok(friendService.updateFriendProfile(userId, friendRequest));
  }

  @PutMapping("/status")
  public ResponseEntity<List<FriendResponse>> updateFriendStatus(@RequestHeader final Long userId,
                                                                 @RequestBody FriendRequest friendRequest) {

    return ResponseEntity.ok(friendService.updateFriendStatus(userId, friendRequest));
  }

  @DeleteMapping("/{friendId}")
  public ResponseEntity<List<FriendResponse>> removeFriendByMemberIdAndFriendId(@RequestHeader final Long userId,
                                                                                @PathVariable final Long friendId) {

    return ResponseEntity.ok(friendService.removeFriend(userId, friendId));
  }
}
