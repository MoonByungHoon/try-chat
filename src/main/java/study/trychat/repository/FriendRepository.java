package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.trychat.entity.Friend;
import study.trychat.repository.querydsl.FriendQuery;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long>, FriendQuery {
  long deleteByMemberIdAndFriendId(Long memberId, Long friendId);

  Friend findByMemberIdAndFriendId(Long memberId, Long friendId);

  List<Friend> findByMemberId(Long memberId);
}
