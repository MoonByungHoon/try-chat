package study.trychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.FriendDto;
import study.trychat.dto.MemberDto;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

  @PostMapping("/add/user/{userId}/friend/{nickName}")
  public ResponseEntity<String> addFriendNickName(@PathVariable("userId") final Long userId,
                                                  @PathVariable("nickName") final String nickName) {

    return ResponseEntity.ok("친구 추가에 성공하였습니다.");
  }

  @GetMapping("/find/friend/{friendId}")
  public ResponseEntity<FriendDto> findFriendByFriendId(@PathVariable("friendId") final Long friendId) {

    return ResponseEntity.ok(new FriendDto("testFriend"));
  }

  @GetMapping("/find/friend-list/{userId}")
  public ResponseEntity<MemberDto> findFriendByUserId(@PathVariable("userId") final Long userId) {
    MemberDto memberDto = new MemberDto(userId, "testUser",
            new FriendDto("testFriend1"), new FriendDto("testFriend2"));

    return ResponseEntity.ok(memberDto);
  }

  @PutMapping("/update/friend-profile/user/{userId}")
  public ResponseEntity<FriendDto> updateFriendProfile(@PathVariable("userId") final Long userId,
                                                       @RequestBody FriendDto friendDto) {

    return ResponseEntity.ok(friendDto);
  }

  @DeleteMapping("/delete/user/{userId}/friend/{friendId}")
  public ResponseEntity<String> removeFriendByUserIdAndFriendId(@PathVariable("userId") final Long userId,
                                                                @PathVariable("friendId") final Long friendId) {

    return ResponseEntity.ok("친구 삭제에 성공하였습니다.");
  }
}
