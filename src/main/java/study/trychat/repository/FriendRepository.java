package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.trychat.entity.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
  long deleteByMemberIdAndFriendId(Long memberId, Long friendId);

  Optional<Friend> findByMemberIdAndFriendId(Long memberId, Long friendId);

  Optional<List<Friend>> findByMemberId(Long memberId);

  @Query("select case when count(f) > 0 then true else false end " +
          "from Friend f join MemberInfo mi on f.friendId = mi.id where mi.id = :memberId and mi.username = :username")
  boolean existsByMemberIdAndUsername(@Param("memberId") Long memberId, @Param("username") String username);
}
