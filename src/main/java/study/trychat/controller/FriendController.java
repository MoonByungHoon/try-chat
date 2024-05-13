package study.trychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.FriendDto;
import study.trychat.service.FriendService;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

  private final FriendService friendService;

  //  유니크 네임으로 검색 친구 추가.
  @PostMapping("/{uniqueName}")
  public ResponseEntity<String> addFriendByNickname(@RequestHeader final Long userId,
                                                    @PathVariable final String uniqueName) {

    friendService.addFriendByNickname(userId, uniqueName);

    return ResponseEntity.ok("친구 추가에 성공하였습니다.");
  }

  @GetMapping("/{friendId}/profile")
  public ResponseEntity<FriendDto> findFriendByFriendId(@PathVariable final Long friendId) {

    return ResponseEntity.ok(new FriendDto("testFriend"));
  }

  @PutMapping("/profile")
  public ResponseEntity<FriendDto> updateFriendProfile(@RequestHeader final Long userId,
                                                       @RequestBody FriendDto friendDto) {

    return ResponseEntity.ok(friendDto);
  }

  @DeleteMapping("/{friendId}")
  public ResponseEntity<String> removeFriendByUserIdAndFriendId(@RequestHeader final Long userId,
                                                                @PathVariable final Long friendId) {

    return ResponseEntity.ok("친구 삭제에 성공하였습니다.");
  }
}
