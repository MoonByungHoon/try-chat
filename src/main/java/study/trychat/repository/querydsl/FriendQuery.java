package study.trychat.repository.querydsl;

import study.trychat.dto.FriendResponse;

import java.util.List;

public interface FriendQuery {
  List<FriendResponse> findFriendsByMemberId(Long memberId);

  boolean existsByUniqueName(Long memberId, String uniqueName);

  FriendResponse findFriendsByMemberIdAndFriendId(Long memberId, Long friendId);
}
