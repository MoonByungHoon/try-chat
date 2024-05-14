package study.trychat.repository.querydsl;

import study.trychat.dto.FriendResponse;

import java.util.List;

public interface FriendQuery {
  List<FriendResponse> findFriendsByUserId(Long userId);

  boolean existsByUniqueName(String uniqueName);
}
