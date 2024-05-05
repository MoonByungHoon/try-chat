package study.trychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.FriendDto;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

  //  닉네임 검색으로 친구 추가.
  @PostMapping(value = "/{nickname}/add")
  public ResponseEntity<String> addFriendNickname(@RequestHeader("userId") final Long userId,
                                                  @PathVariable("nickname") final String nickname) {

    return ResponseEntity.ok("친구 추가에 성공하였습니다.");
  }

  @GetMapping("/{friendId}/profile")
  public ResponseEntity<FriendDto> findFriendByFriendId(@PathVariable("friendId") final Long friendId) {

    return ResponseEntity.ok(new FriendDto("testFriend"));
  }

  @PutMapping("/profile")
  public ResponseEntity<FriendDto> updateFriendProfile(@RequestHeader("userId") final Long userId,
                                                       @RequestBody FriendDto friendDto) {

    return ResponseEntity.ok(friendDto);
  }

  @DeleteMapping("/{friendId}")
  public ResponseEntity<String> removeFriendByUserIdAndFriendId(@RequestHeader("userId") final Long userId,
                                                                @PathVariable("friendId") final Long friendId) {

    return ResponseEntity.ok("친구 삭제에 성공하였습니다.");
  }
}
