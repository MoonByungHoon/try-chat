package study.trychat.friend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.trychat.member.domain.Member;

import java.util.Optional;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {
  long deleteByMemberIdAndFriendId(Long memberId, Long friendId);

  Optional<FriendShip> findByMemberIdAndFriendId(Long memberId, Long friendId);

  @Query("select count (*) from FriendShip f join MemberInfo mi on f.member = :member " +
          "where mi.username = :username")
  Long existsByMemberAndUsername(@Param("member") Member member, @Param("username") String username);

  Optional<FriendShip> findByFriendId(Long friendId);
}
