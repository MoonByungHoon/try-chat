package study.trychat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.trychat.entity.FriendShip;
import study.trychat.entity.Member;

import java.util.List;
import java.util.Optional;

public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {
  long deleteByMemberAndFriendId(Member member, Long friendId);

  Optional<FriendShip> findByMemberAndFriendId(Member member, Long friendId);

  Optional<List<FriendShip>> findByMemberId(Long memberId);

  @Query("select count (*) from FriendShip f join MemberInfo mi on f.member = :member " +
          "where mi.username = :username")
  Long existsByMemberAndUsername(@Param("member") Member member, @Param("username") String username);

  Optional<FriendShip> findByFriendId(Long friendId);
}
