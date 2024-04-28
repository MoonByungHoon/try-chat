package study.trychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.trychat.dto.FriendDto;
import study.trychat.dto.MemberDto;
import study.trychat.entity.Friend;

@RestController
@RequestMapping("friends")
@RequiredArgsConstructor
public class FriendController {

  @PostMapping("/add/user/{userId}/friend/{nickName}")
  public ResponseEntity<?> addFriendNickName(@PathVariable("userId") final Long userId,
                                             @PathVariable("nickName") final String nickName) {

    return ResponseEntity.ok().body(new Friend(userId, nickName));
  }

  @GetMapping("/find/friend/{friendId}")
  public ResponseEntity<?> findFriendByFriendId(@PathVariable("friendId") final Long friendId) {

    return ResponseEntity.ok().body(new Friend(friendId, "testFriend"));
  }

  @GetMapping("/find/friend-list/{userId}")
  public ResponseEntity<?> findFriendByUserId(@PathVariable("userId") final Long userId) {
    MemberDto memberDto = new MemberDto(userId, "testUser");

    memberDto.addFriendDto(new FriendDto("testFriend1"), new FriendDto("testFriend2"));

    return ResponseEntity.ok().body(memberDto);
  }

  @PutMapping("/update/friend-profile/user/{userId}")
  public ResponseEntity<?> updateFriendProfile(@PathVariable("userId") final Long userId,
                                               @RequestBody FriendDto friendDto) {

    return ResponseEntity.ok().body(friendDto);
  }

  @DeleteMapping("/delete/user/{userId}/friend/{friendId}")
  public ResponseEntity<?> removeFriendByUserIdAndFriendId(@PathVariable("userId") final Long userId,
                                                           @PathVariable("friendId") final Long friendId) {

    return ResponseEntity.ok(200);
  }
}
