package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.trychat.entity.Friend;
import study.trychat.repository.querydsl.FriendQuery;

public interface FriendRepository extends JpaRepository<Friend, Long>, FriendQuery {
  long deleteByMemberIdAndFriendId(Long memberId, Long friendId);
}
