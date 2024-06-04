package study.trychat.repository.querydsl;

import study.trychat.dto.FriendResponse;

import java.util.List;

public interface FriendQuery {
  List<FriendResponse> findFriendsByMemberId(Long memberId);

  boolean duplicateFriend(Long memberId, String username);

  FriendResponse findFriendsByMemberIdAndFriendId(Long memberId, Long friendId);
}
